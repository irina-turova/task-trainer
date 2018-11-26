package com.trainer.routers

import controllers.SubthemeController
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.subthemes() {

    route("subthemes") {
        get("{themeName}") {
            val themeName = call.parameters["themeName"]
            val subthemes = SubthemeController.get(themeName)
            call.respond(subthemes)
        }
    }
}