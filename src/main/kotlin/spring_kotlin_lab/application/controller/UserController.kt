package spring_kotlin_lab.application.controller

import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import spring_kotlin_lab.application.request.UserRequest
import spring_kotlin_lab.domain.model.User
import spring_kotlin_lab.domain.service.UserService

@RestController
@RequestMapping("/users")
class UserController(
    private val service: UserService,
    private val log: Logger = LoggerFactory.getLogger(UserController::class.java)
) {

    @PostMapping
    fun createUser(@RequestBody @Valid req: UserRequest): User {

        log.info("User Controller - Received request to create user: $req")

        val user = service.create(req.toDomain())

        log.info("User Service - User created: $user")
        return user
    }

}