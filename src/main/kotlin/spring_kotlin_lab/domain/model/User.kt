package spring_kotlin_lab.domain.model

import spring_kotlin_lab.infrastructure.entity.UserEntity
import java.util.*

class User private constructor(
    val id: UUID?,
    val name: String,
    val email: String
) {
    companion object {

        fun new(name: String, email: String): User =
            User(
                id = null,
                name = name,
                email = email
            )

        fun restore(id: UUID, name: String, email: String): User =
            User(
                id = id,
                name = name,
                email = email
            )

    }

    fun toEntity(): UserEntity =
        UserEntity(
            id = this.id,
            name = this.name,
            email = this.email
        )
}
