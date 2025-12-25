package spring_kotlin_lab.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import spring_kotlin_lab.infrastructure.entity.UserEntity
import java.util.UUID

interface UserJpaRepository : JpaRepository<UserEntity, UUID> {
}