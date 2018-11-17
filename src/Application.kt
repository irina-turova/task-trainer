package com.trainer

import apache.cayenne.mappings.Difficulty
import com.trainer.controllers.TaskController
import controllers.SubthemeController
import com.trainer.controllers.ThemeController
import controllers.DifficultyController
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.content.default
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.http.content.staticRootFolder
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.File
import java.text.DateFormat

fun main(args: Array<String>) {

    val server = embeddedServer(Netty, 8075) {

        install(ContentNegotiation) {
            gson {
                setDateFormat(DateFormat.LONG)
                setPrettyPrinting()
            }
        }

        routing {
            trace { application.log.trace(it.buildText()) }

            static("") {
                staticRootFolder = File("client/dist")
                files("css")
                files("js")
                files("")
                default("index.html")
            }

            route("api") {
                route("tasks") {
                    get {
                        val task = TaskController.getRandom()
                        call.respond(task)
                    }
                }
                route("themes") {
                    get {
                        val themes = ThemeController.get()
                        call.respond(themes)
                    }
                }
                route("subthemes") {
                    get("{themeName}") {
                        val themeName = call.parameters["themeName"]
                        val subthemes= SubthemeController.get(themeName)
                        call.respond(subthemes)
                    }
                }
                route("difficulties") {
                    get("{subthemeName}") {
                        val subtheme = call.parameters["subthemeName"]
                        val difficulties = DifficultyController.get(subtheme)
                        call.respond(difficulties)
                    }
                }
                route("users") {
                    get() {

                    }
                }
            }
        }
    }
    server.start(wait = true)
}