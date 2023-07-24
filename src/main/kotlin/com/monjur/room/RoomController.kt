package com.monjur.room

import com.monjur.data.MessegeDatSource
import com.monjur.data.models.Messege
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.litote.kmongo.json

import java.util.concurrent.ConcurrentHashMap

class RoomController(val messageDataSource:MessegeDatSource) {

    private val members=ConcurrentHashMap<String,MemberInChatRoom>()

    fun onJoinToRoom(
        userName:String,
        sessionID:String,
        webSocket:WebSocketSession
    ){
        if (members.contains(userName)){
            throw AlreadyExistException()
        }
        members[userName]=MemberInChatRoom(
            userName = userName,
            sessionId=sessionID,
            webSocket=webSocket
        )
    }

    suspend fun sendMsg(senderName:String,message:String){
        members.values.forEach { aMember ->
            val messageEntry=Messege(
                msgText = message,
                userName = senderName,
                timeStamp = System.currentTimeMillis()
            )
            messageDataSource.insertMessage(messageEntry)

            val jsonMsg= Json.encodeToString(messageEntry)
            println(jsonMsg)
            aMember.webSocket.send(Frame.Text(jsonMsg))
        }
    }

    suspend fun getAllMsg():List<Messege>{
        return messageDataSource.getAllMessage()
    }

    suspend fun disconnect(userName:String){
        members[userName]?.webSocket?.close()
        if (members.contains(userName)) members.remove(userName)
    }

}