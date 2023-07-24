package com.monjur.plugins

import com.monjur.room.RoomController
import com.monjur.routes.ChatSocketRoute
import com.monjur.routes.getAllMsg
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val controller by inject<RoomController>()
    install(Routing) {
        ChatSocketRoute(controller)
        getAllMsg(controller)
    }
}
