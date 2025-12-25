package spring_kotlin_lab.domain.repository

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import spring_kotlin_lab.domain.model.User
import spring_kotlin_lab.infrastructure.entity.UserEntity
import spring_kotlin_lab.infrastructure.repository.UserJpaRepository

@Repository
class UserRepository(
    private val userRepository: UserJpaRepository,
    private val log: Logger = LoggerFactory.getLogger(UserRepository::class.java)
) {

    fun create(user: User): User {

        log.info("User Repository - Saving User to database: $user")

        val userEntity: UserEntity = userRepository.save(user.toEntity())

        log.info("User Repository - User saved to database: $userEntity")

        return userEntity.toDomain()

    }

}