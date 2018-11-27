package com.trainer.routers

import com.trainer.controllers.ThemeController
import io.ktor.application.call
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.themes() {

    route("themes") {
        get {
            val themes = ThemeController.get()
            call.respond(themes)
        }
        post {
            val result = ThemeController.store(call.receiveText())

            if (result.first.value == 200) {
                val theme = result.second as apache.cayenne.mappings.Theme
                call.respond(result.first, theme.name)
            } else
                call.respond(result.first, result.second)
        }
    }
}