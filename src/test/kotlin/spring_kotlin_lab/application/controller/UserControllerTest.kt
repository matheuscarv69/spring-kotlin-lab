package spring_kotlin_lab.application.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import spring_kotlin_lab.domain.model.User
import spring_kotlin_lab.domain.service.UserService
import java.util.*
import java.util.stream.Stream

@WebMvcTest(controllers = [UserController::class])
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var userService: UserService

    @Test
    fun `should create user successfully`() {

        // scenario
        val userId = UUID.randomUUID();
        val name = "John Doe"
        val email = "john.doe@gmail.com"
        val requestBody = """
            {
                "name": "$name",
                "email": "$email"
             }
        """.trimIndent()

        val expectedUser = User.restore(userId, name, email)
        every { userService.create(any()) } returns expectedUser

        // action & validation
        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(userId.toString()) }
            jsonPath("$.name") { value(name) }
            jsonPath("$.email") { value(email) }
        }

        verify(exactly = 1) { userService.create(any()) }

    }

    @Test
    fun `should return 400 bad request when name is empty`() {

        // scenario
        val requestBody = """
            {
                "name": "",
                "email": "john.doe@gmail.com"
            }
            """.trimIndent()

        // action & validation
        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isBadRequest() }
        }

    }

    @Test
    fun `should return 400 bad request when email is empty`() {

        // scenario
        val requestBody = """
            {
                "name": "john doe",
                "email": ""
            }
            """.trimIndent()

        // action & validation
        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isBadRequest() }
        }

    }

    @ParameterizedTest
    @MethodSource("invalidRequestBodies")
    fun `should return 400 bad request when request is invalid`(requestBody: String) {

        // action & validation
        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isBadRequest() }
        }

    }

    companion object {
        @JvmStatic
        fun invalidRequestBodies(): Stream<String> = Stream.of(
            // Empty name
            """
            {
                "name": "",
                "email": "john.doe@gmail.com"
            }
            """.trimIndent(),

            // Empty email
            """
            {
                "name": "John Doe",
                "email": ""
            }
            """.trimIndent(),

            // Invalid email
            """
            {
                "name": "John Doe",
                "email": "invalid-email"
            }
            """.trimIndent(),

            // Missing name
            """
            {
                "email": "john.doe@gmail.com"
            }
            """.trimIndent(),

            // Missing email
            """
            {
                "name": "John Doe"
            }
            """.trimIndent()
        )
    }

}
