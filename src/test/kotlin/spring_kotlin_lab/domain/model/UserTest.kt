package spring_kotlin_lab.domain.model

import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserTest {

    @Test
    fun `should create a new user without id`() {
        // scenario
        val user = User.new(
            name = "John Doe",
            email = "john.doe@gmail.com"
        )

        // action / validation

        assertNull(user.id)
        assertEquals("John Doe", user.name)

    }

    @Test
    fun `should restore a user with id`() {
        // scenario
        val userId = UUID.randomUUID()
        val name = "John Doe"
        val email = "john.doe@gmail.com"

        // action / validation
        val user = User.restore(
            id = userId,
            name = name,
            email = email
        )

        assertEquals(userId, user.id)
        assertEquals(name, user.name)
        assertEquals(email, user.email)

    }

    @Test
    fun `should convert user to entity`() {
        // scenario
        val userId = UUID.randomUUID()

        val user = User.restore(
            id = userId,
            name = "John Doe",
            email = "john.doe@gmail.com"
        )

        // action
        val userEntity = user.toEntity()

        // validation
        assertEquals(user.id, userEntity.id)
        assertEquals(user.name, userEntity.name)
        assertEquals(user.email, userEntity.email)

    }

}
