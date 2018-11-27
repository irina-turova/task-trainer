package com.trainer

import com.trainer.routers.*
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.content.default
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.http.content.staticRootFolder
import io.ktor.response.respondText
import io.ktor.routing.get
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
                disableInnerClassSerialization()
            }
        }

        install(Sessions) {

            // Secret httponly session to identify user
            cookie<LoginSession>("LOGIN_SESSION") {
                cookie.path = "/"
                cookie.httpOnly = true
                val secretHashKey = hex("6819b57a326945c1968f45236589")
                transform(SessionTransportTransformerMessageAuthentication(secretHashKey, "HmacSHA256"))
            }
        }

        routing {
            trace { application.log.trace(it.buildText()) }

            route("api") {

                user()
                tasks()
                themes()
                subthemes()
                difficulties()
                chat()

                // Пример запроса, в котором проверяется сессия пользователя
                get("hello") {
                    val session = call.sessions.get<LoginSession>()

                    // В итоге в id находится строка с id пользователя или null, если куки некоррекктные
                    println(session?.id)
                    call.respondText(session?.id ?: "")
                }
            }

            listOf("", "/*").forEach {
                static(it) {
                    staticRootFolder = File("client/dist")
                    files("css")
                    files("js")
                    files("")
                    default("index.html")
                }
            }

        }
    }
    server.start(wait = true)
}