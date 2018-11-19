package com.trainer

import com.trainer.controllers.TaskController
import com.trainer.controllers.ThemeController
import com.trainer.controllers.UserController
import controllers.DifficultyController
import controllers.SubthemeController
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.auth.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.content.default
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.http.content.staticRootFolder
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.*
import io.ktor.util.hex
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

        install(Authentication) {
            form("login") {
                userParamName = "user"
                passwordParamName = "password"
                challenge = FormAuthChallenge.Unauthorized
                validate { credentials -> UserController.find(credentials) }
            }
        }

        install(Sessions) {
            val secretHashKey = hex("6819b57a326945c1968f45236589")

            cookie<SimpleSession>("SESSION_FEATURE_SESSION") {
                cookie.path = "/"
                transform(SessionTransportTransformerMessageAuthentication(secretHashKey, "HmacSHA256"))
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

                authenticate("login") {
                    post("login") {
                        val principal = call.principal<UserIdPrincipal>()
                        println(principal)
                        call.sessions.set(SimpleSession(principal?.name ?: ""))
                        call.respondText("Success")
                    }
                }

                // Пример запроса, в котором проверяется сессия пользователя
                get("hello") {
                    val session = call.sessions.get<SimpleSession>()

                    // В итоге в token находится строка с id пользователя или null, если куки некоррекктные
                    println(session?.token)
                    call.respondText(session?.token ?: "")
                }

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
                route("themes") {
                    get {
                        val themes = ThemeController.get()
                        call.respond(themes)
                    }
                }
                route("subthemes") {
                    get("{themeName}") {
                        val themeName = call.parameters["themeName"]
                        val subthemes = SubthemeController.get(themeName)
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
                    post {
                        // Todo: implement registration
                    }
                }
            }
        }
    }
    server.start(wait = true)
}