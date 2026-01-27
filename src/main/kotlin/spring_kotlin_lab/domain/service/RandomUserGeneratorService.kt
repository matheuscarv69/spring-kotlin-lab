package spring_kotlin_lab.domain.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import spring_kotlin_lab.domain.model.User
import kotlin.random.Random

@Service
class RandomUserGeneratorService(
    private val userService: UserService,
    private val log: Logger = LoggerFactory.getLogger(RandomUserGeneratorService::class.java)
) {

    private val firstNames = listOf(
        "João", "Maria", "Pedro", "Ana", "Carlos", "Julia",
        "Lucas", "Beatriz", "Rafael", "Fernanda", "Gabriel", "Camila"
    )

    private val lastNames = listOf(
        "Silva", "Santos", "Oliveira", "Souza", "Rodrigues",
        "Ferreira", "Almeida", "Costa", "Carvalho", "Martins"
    )

    fun generate(quantity: Int): List<User> {
        log.info("Generating $quantity random users...")

        val users = (1..quantity).map {
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val randomNumber = Random.nextInt(1000, 9999)

            val name = "$firstName $lastName"
            val email = "${firstName.lowercase()}.${lastName.lowercase()}$randomNumber@email.com"

            val user = User.new(name, email)
            userService.create(user)
        }

        log.info("Successfully generated ${users.size} users")
        return users
    }
}
