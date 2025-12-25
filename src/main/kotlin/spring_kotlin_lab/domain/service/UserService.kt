package spring_kotlin_lab.domain.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import spring_kotlin_lab.domain.model.User
import spring_kotlin_lab.domain.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val log: Logger = LoggerFactory.getLogger(UserService::class.java)
) {

    fun create(user: User): User {

        log.info("User Service - Creating User: $user")

        val savedUser = userRepository.create(user)

        log.info("User Service - User created: $savedUser")

        return savedUser

    }

}