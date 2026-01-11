package spring_kotlin_lab.domain.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import spring_kotlin_lab.domain.model.User
import spring_kotlin_lab.domain.repository.UserRepository
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class UserServiceTest {

    private val repository: UserRepository = mockk<UserRepository>()
    private val service: UserService = UserService(repository)

    @Test
    fun `should create a user successfully`() {
        // scenario
        val user: User = User.new("John Doe", "john.doe@gmail.com")
        val userId = UUID.randomUUID();
        val savedUser: User = User.restore(userId, user.name, user.email)

        every { repository.create(user) } returns savedUser

        // action
        val result: User = service.create(user)

        // validation
        assertEquals(savedUser, result)

        verify(exactly = 1) { repository.create(user) }

    }

}