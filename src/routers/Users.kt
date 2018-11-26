package com.trainer.routers

import com.trainer.LoginSession
import com.trainer.UserData
import com.trainer.controllers.UserController
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.application
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.sessions.clear
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import org.apache.cayenne.Cayenne

fun Route.user() {

    application.install(Authentication) {
        form("login") {
            userParamName = "user"
            passwordParamName = "password"
            challenge = FormAuthChallenge.Unauthorized
            validate { credentials -> UserController.find(credentials) }
        }
    }

    route("users") {

        authenticate("login") {
            post("login") {
                val principal = call.principal<UserIdPrincipal>()
                println(principal)
                if (principal == null)
                    return@post
                call.sessions.set(LoginSession(principal.name))
                val user = UserController.get(principal.name.toInt())
                call.respond(UserData(user.firstName, user.lastName, user.login, user.role.name))
            }
        }

        post("logout") {
            call.sessions.clear<LoginSession>()
            call.respond("")
        }

        post("register") {
            val result = UserController.store(call.receiveText())

            if (result.first.value == 200) {
                val user = result.second as apache.cayenne.mappings.User
                call.sessions.set(LoginSession(Cayenne.pkForObject(user).toString()))
                call.respond(result.first, UserData(user.firstName, user.lastName, user.login, user.role.name))
            } else
                call.respond(result.first, result.second)
        }
    }
}