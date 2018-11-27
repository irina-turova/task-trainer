package com.trainer.routers

import apache.cayenne.mappings.Task
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.trainer.LoginSession
import com.trainer.controllers.TaskController
import com.trainer.controllers.ThemeController
import io.ktor.application.call
import io.ktor.gson.GsonConverter
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import org.omg.CORBA.Object

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
                val task = result.second as apache.cayenne.mappings.Task
                call.respond(result.first, task)
            }
        }
    }
}