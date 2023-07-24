package com.monjur.plugins

import com.monjur.session.UserSession
import io.ktor.server.sessions.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.*

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<UserSession>("MY_SESSION")
    }
    intercept(ApplicationCallPipeline.Features){
        if (call.sessions.get<UserSession>()==null){
            val username= call.parameters["username"] ?:"Guest"
            call.sessions.set(UserSession(username, generateNonce()))
        }
    }

}
