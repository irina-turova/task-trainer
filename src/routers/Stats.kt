package com.trainer.routers

import com.trainer.LoginSession
import controllers.DifficultyController
import controllers.StatsController
import io.ktor.application.call
import io.ktor.http.fromHttpToGmtDate
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.sessions.sessions

fun Route.stats() {

    route("stats") {
        get {
            var params = call.parameters
                /*
            val chartData = StatsController.get(
                call.parameters["startDate"],
                call.parameters["endDate"],
                call.sessions.get<LoginSession>()!!.id.toInt(),
                call.parameters["themes"])
            call.respond(chartData)*/
            call.respond("cool and good")
        }
    }
}