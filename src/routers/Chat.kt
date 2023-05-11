package com.trainer.routers

import com.google.gson.GsonBuilder
import com.trainer.LoginSession
import com.trainer.UserData
import com.trainer.controllers.UserController
import io.ktor.application.install
import io.ktor.http.cio.websocket.*
import io.ktor.routing.Route
import io.ktor.routing.application
import io.ktor.routing.route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.websocket.webSocket
import java.util.*

data class SendUnit(val user: UserData, val message: String)

fun Route.chat() {

    application.install(io.ktor.websocket.WebSockets) {
        pingPeriod = java.time.Duration.ofSeconds(15)
        timeout = java.time.Duration.ofSeconds(120)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    route("chat") {
        val clients = Collections.synchronizedSet(LinkedHashSet<ChatClient>())

        webSocket("") {
            val loginSession = call.sessions.get<LoginSession>()
                ?: return@webSocket
            val user = UserController.get(loginSession.id.toInt())

            val client = ChatClient(this, loginSession.id.toInt(), UserData(user.firstName, user.lastName, user.login, user.role.name))
            clients += client

            val gson = GsonBuilder().setPrettyPrinting().create()
            try {
                while (true) {
                    val frame = incoming.receive()
                    when (frame) {
                        is Frame.Text -> {
                            val message = frame.readText()
                            println("Message: $message")

                            for (other in clients.toList()) {
                                val data = SendUnit(client.userData, message)
                                other.session.outgoing.send(Frame.Text(gson.toJson(data)))
                            }
                        }
                    }
                }
            } finally {
                clients -= client
            }
        }
    }
}

data class ChatClient(val session: DefaultWebSocketSession, val id: Int, val userData: UserData)
