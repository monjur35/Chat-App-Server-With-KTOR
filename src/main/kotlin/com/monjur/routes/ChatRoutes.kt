package com.monjur.routes

import com.monjur.room.AlreadyExistException
import com.monjur.room.RoomController
import com.monjur.session.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.ChatSocketRoute(controller: RoomController) {
    webSocket {
        val session = call.sessions.get<UserSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.TRY_AGAIN_LATER, "Session Expired"))
            return@webSocket
        }
        try {
            controller.onJoinToRoom(
                userName = session.userName,
                sessionID = session.session,
                webSocket = this
            )
            incoming.consumeEach {
                if (it is Frame.Text) {
                    controller.sendMsg(
                        senderName = session.userName,
                        message = it.readText()
                    )
                }
            }
        } catch (e: AlreadyExistException) {
            call.respond(HttpStatusCode.Conflict, "User Already Exist")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, e.localizedMessage)
        } finally {
            controller.disconnect(session.userName)
        }
    }
}

fun Route.getAllMsg(controller: RoomController) {
    get("/messages") {
        call.respond(
            HttpStatusCode.OK,
            controller.getAllMsg()
        )
    }

}