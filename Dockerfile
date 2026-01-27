# ===========================================
# Stage 1: Build
# Usa imagem com JDK + Gradle para compilar
# ===========================================
FROM gradle:8.14-jdk17 AS builder

WORKDIR /app

# Copia arquivos de configuração do Gradle primeiro (cache de dependências)
COPY build.gradle.kts settings.gradle.kts gradle.properties* ./
COPY gradle ./gradle

# Baixa dependências (camada cacheada se não mudar)
RUN gradle dependencies --no-daemon || true

# Copia código fonte
COPY src ./src

# Builda o JAR (sem rodar testes para acelerar)
RUN gradle bootJar --no-daemon

# ===========================================
# Stage 2: Runtime
# Usa imagem JRE leve para executar
# ===========================================
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Cria usuário não-root para segurança
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

# Copia o JAR do stage de build
COPY --from=builder /app/build/libs/*.jar app.jar

# Muda para usuário não-root
USER appuser

# Variáveis de ambiente padrão
ENV SPRING_PROFILES_ACTIVE=job
ENV JAVA_OPTS="-Xmx256m -Xms128m"

# Executa o JAR e encerra quando terminar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
