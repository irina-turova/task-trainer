package com.trainer.routers

import com.trainer.controllers.TaskController
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.tasks() {

    route("tasks") {
        get("random") {
            val task = TaskController.getRandom()
            print(task)
            call.respond(task ?: "")
        }
        get("{subthemeName}/{difficultyName}/{taskId}") {
            val task = TaskController.get(
                call.parameters["subthemeName"],
                call.parameters["difficultyName"],
                call.parameters["taskId"]
            )
            call.respond(task ?: "")
        }
    }
}