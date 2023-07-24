package com.monjur.room

import io.ktor.websocket.*

data class MemberInChatRoom(
    val userName:String,
    val sessionId:String,
    val webSocket:WebSocketSession,
)
