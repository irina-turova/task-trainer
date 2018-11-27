package com.trainer.routers

import controllers.SubthemeController
import io.ktor.application.call
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.subthemes() {

    route("subthemes") {
        get("{themeName}") {
            val themeName = call.parameters["themeName"]
            val subthemes = SubthemeController.get(themeName)
            call.respond(subthemes)
        }
        post {
            val result = SubthemeController.store(call.receiveText())

            if (result.first.value == 200) {
                val subtheme = result.second as apache.cayenne.mappings.Subtheme
                call.respond(result.first, subtheme.name)
            } else
                call.respond(result.first, result.second)
        }
    }
}