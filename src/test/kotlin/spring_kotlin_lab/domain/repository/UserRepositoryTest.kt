package spring_kotlin_lab.domain.repository

import io.mockk.every
import io.mockk.mockk
import spring_kotlin_lab.domain.model.User
import spring_kotlin_lab.infrastructure.entity.UserEntity
import spring_kotlin_lab.infrastructure.repository.UserJpaRepository
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserRepositoryTest {

    private val jpaRepository = mockk<UserJpaRepository>()
    private val repository = UserRepository(jpaRepository)

    @Test
    fun `should create new user`() {

        // scenario
        val user: User = User.new("John Doe", "john.doe@gmail.com")

        every { jpaRepository.save(any()) } answers {
            val entity = firstArg<UserEntity>()
            UserEntity(UUID.randomUUID(), entity.name, entity.email)
        }

        // action
        val result: User = repository.create(user)

        // validation
        assertNotNull(result.id)
        assertEquals(user.name, result.name)
        assertEquals(user.email, result.email)

    }

}