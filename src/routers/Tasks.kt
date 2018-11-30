package com.trainer.routers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trainer.LoginSession
import com.trainer.controllers.TaskController
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.sessions.get
import io.ktor.sessions.sessions

fun Route.tasks() {

    route("tasks") {
        get("{subthemeName}/{difficultyName}") {
            val session = call.sessions.get<LoginSession>()
            if (session == null) {
                call.respond(HttpStatusCode.Forbidden.description("Not logged in"))
                return@get
            }
            val task = TaskController.getRandom(session.id.toInt(), call.parameters["subthemeName"], call.parameters["difficultyName"])
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
        get("taskimage/{imagetype}/{taskId}") {
            val image = TaskController.getTaskImage(
                call.parameters["imagetype"],
                call.parameters["taskId"]
            )
            call.respond(image.first, image.second)
        }
        post("check"){
            val json = call.receiveText()
            val jsonAsMap: Map<String, String> = Gson().fromJson(json, object : TypeToken<Map<String, String>>() {}.type)
            call.respond(
                TaskController.check(
                    jsonAsMap["taskId"]!!.toInt(),
                    call.sessions.get<LoginSession>()!!.id.toInt(),
                    jsonAsMap["actualAnswer"].toString()))
        }
        post {
            val session = call.sessions.get<LoginSession>()
            if (session == null) {
                call.respond(HttpStatusCode.Forbidden.description("Not logged in"))
                return@post
            }
            val result = TaskController.store(call.receiveText(), session.id.toInt())

            if (result.first.value == 200) {
                // val task = result.second as apache.cayenne.mappings.Task
                call.respond(result.first)
            } else {
                call.respond(result.first, result.second)
            }
        }
    }
}