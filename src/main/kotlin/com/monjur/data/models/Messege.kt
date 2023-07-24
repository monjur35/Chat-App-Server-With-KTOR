package com.monjur.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Messege(
    @BsonId
    val id:String=ObjectId().toString(),
    val msgText:String,
    val userName:String,
    val timeStamp:Long,
)
