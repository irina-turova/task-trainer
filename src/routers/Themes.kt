package com.trainer.routers

import com.trainer.controllers.ThemeController
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.themes() {

    route("themes") {
        get {
            val themes = ThemeController.get()
            call.respond(themes)
        }
    }
}