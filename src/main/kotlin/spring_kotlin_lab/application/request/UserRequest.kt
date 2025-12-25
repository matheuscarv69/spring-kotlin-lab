package spring_kotlin_lab.application.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import spring_kotlin_lab.domain.model.User

data class UserRequest(
    @NotEmpty val name: String,
    @NotEmpty @Email val email: String
) {

    fun toDomain(): User =
        User.new(
            name = this.name,
            email = this.email
        )

}
