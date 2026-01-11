package spring_kotlin_lab.application.request

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserRequestTest {

    @Test
    fun `should convert UserRequest to User domain model`() {
        // scenario
        val userRequest = UserRequest(
            name = "Jane Doe",
            email = "john.doe@gmail.com"
        )

        // action
        val userDomain = userRequest.toDomain()

        // validation
        assertNull(userDomain.id)
        assertEquals(userRequest.name, userDomain.name)
        assertEquals(userRequest.email, userDomain.email)

    }

}