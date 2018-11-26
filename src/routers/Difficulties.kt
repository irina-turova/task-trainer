package com.trainer.routers

import controllers.DifficultyController
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.difficulties() {

    route("difficulties") {
        get("{subthemeName}") {
            val subtheme = call.parameters["subthemeName"]
            val difficulties = DifficultyController.get(subtheme)
            call.respond(difficulties)
        }
    }
}