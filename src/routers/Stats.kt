package com.trainer.routers

import com.google.gson.Gson
import com.trainer.LoginSession
import controllers.StatsController
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun Route.stats() {

    route("stats") {
        get("{startDate}/{endDate}/{themes}") {
            val session = call.sessions.get<LoginSession>()
            if (session == null) {
                call.respond(HttpStatusCode.Forbidden.description("Not logged in"))
                return@get
            }
            val chartData = StatsController.getChartData(
                LocalDate.parse(call.parameters["startDate"], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.parse(call.parameters["endDate"], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                call.sessions.get<LoginSession>()?.id!!.toInt(),
                call.parameters["themes"]!!.toString().split(","))
            call.respond(chartData)
        }
    }
}