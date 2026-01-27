# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A Kotlin and Spring Boot learning project implementing a REST API with PostgreSQL database. Uses modern Spring Boot 4.0.1 with Kotlin 2.2.21 and Java 17.

## Build & Development Commands

### Prerequisites
- Java 17 or later (required for Gradle 9.2.1)
- Docker (for PostgreSQL database)

### Common Commands

```bash
# Start PostgreSQL database
docker-compose up -d

# Build the project
./gradlew build

# Run tests
./gradlew test

# Run a specific test class
./gradlew test --tests "spring_kotlin_lab.domain.service.UserServiceTest"

# Run a specific test method
./gradlew test --tests "spring_kotlin_lab.domain.service.UserServiceTest.should create a user successfully"

# Run the application
./gradlew bootRun

# Clean build artifacts
./gradlew clean
```

## Architecture

This project follows **Clean Architecture** (also known as Hexagonal Architecture or Ports and Adapters) with clear separation of concerns across three layers:

### Layer Structure

```
application/     → Controllers, Request/Response DTOs (presentation layer)
domain/          → Business logic, domain models, repository interfaces
infrastructure/  → JPA entities, repository implementations, external concerns
```

### Key Architectural Patterns

**Domain Model as Source of Truth**: The `domain/model` package contains the core business entities (e.g., `User.kt`) with factory methods:
- `User.new()` - creates new domain objects without ID
- `User.restore()` - reconstructs existing domain objects with ID

**Repository Pattern with Abstraction**:
- `domain/repository/UserRepository.kt` - domain-level repository that orchestrates JPA operations and handles domain↔entity conversions
- `infrastructure/repository/UserJpaRepository.kt` - Spring Data JPA interface extending `JpaRepository`
- The domain repository delegates to JPA repository and manages `toEntity()`/`toDomain()` conversions

**Entity-Domain Mapping**:
- `infrastructure/entity/UserEntity.kt` - JPA entity with database annotations
- Conversion methods: `User.toEntity()` and `UserEntity.toDomain()` keep layers decoupled

**Request DTOs**: Application layer (`application/request`) contains validation-annotated request objects (e.g., `UserRequest`) that convert to domain models via `toDomain()` methods.

### Data Flow

```
Controller → Service → Domain Repository → JPA Repository
   ↓           ↓            ↓                    ↓
Request    Domain       Domain              Entity
  DTO      Model        Model              (JPA)
```

Conversions happen at layer boundaries to maintain separation.

## Database

- **Type**: PostgreSQL 16.11
- **Container**: `postgres_kt_lab` (Docker Compose)
- **Connection**: `localhost:5432`, user: `postgres`, password: `password`
- **Schema Management**: JPA auto-DDL (`spring.jpa.hibernate.ddl-auto=update`)

## Testing

- Uses **JUnit 5** for test framework
- Uses **MockK** (not Mockito) for mocking in Kotlin tests
- Uses **SpringMockK 5.x** for Spring Boot integration tests (`@MockkBean`, `@SpykBean`)
  - Important: Spring Boot 4.x requires SpringMockK 5.x (version 4.x is for Spring Boot 3.x)
- Test structure follows same package hierarchy as source code
- Tests use descriptive backtick method names (Kotlin convention)

Example test pattern:
```kotlin
@Test
fun `should create a user successfully`() {
    // scenario - setup mocks and data
    // action - execute the method under test
    // validation - assert results and verify interactions
}
```

## Kotlin Specifics

- **All-open plugin** configured for JPA entities (`@Entity`, `@MappedSuperclass`, `@Embeddable`) to work around Kotlin's final-by-default classes
- **Compiler flags**: `-Xjsr305=strict` for strict null-safety, `-Xannotation-default-target=param-property` for constructor property annotations
- Uses Jackson Kotlin module for JSON serialization
- Leverages Kotlin's `data class` for DTOs and constructor-based dependency injection