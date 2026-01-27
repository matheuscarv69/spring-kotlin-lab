package spring_kotlin_lab.application.job

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import spring_kotlin_lab.domain.service.RandomUserGeneratorService

@Component
@Profile("job")
class UserGeneratorJobRunner(
    private val randomUserGeneratorService: RandomUserGeneratorService,
    private val log: Logger = LoggerFactory.getLogger(UserGeneratorJobRunner::class.java)
) : CommandLineRunner {

    override fun run(vararg args: String) {
        log.info("=== Starting User Generator Job ===")

        val quantity = args.firstOrNull()?.toIntOrNull() ?: DEFAULT_QUANTITY

        log.info("Generating $quantity random users...")

        val users = randomUserGeneratorService.generate(quantity)

        log.info("=== Job completed! Generated ${users.size} users ===")
    }

    companion object {
        private const val DEFAULT_QUANTITY = 5
    }
}
