package spring_kotlin_lab.infrastructure.entity

import jakarta.persistence.*
import spring_kotlin_lab.domain.model.User
import java.util.*

@Entity
@Table(name = "users")
class UserEntity(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    val name: String,
    val email: String
) {

    fun toDomain(): User =
        User.restore(
            id = this.id!!,
            name = this.name,
            email = this.email
        )

}