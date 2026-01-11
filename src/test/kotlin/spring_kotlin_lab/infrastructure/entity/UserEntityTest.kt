package spring_kotlin_lab.infrastructure.entity

import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class UserEntityTest {

    @Test
    fun `should to convert entity to domain`() {
        // scenario
        val userId = UUID.randomUUID()
        val name = "John Doe"
        val email = "john.doe@gmail.com"

        val userEntity = UserEntity(userId, name, email)

        // action
        val userDomain = userEntity.toDomain()

        // validation
        assertEquals(userId, userDomain.id)
        assertEquals(name, userDomain.name)
        assertEquals(email, userDomain.email)

    }

}