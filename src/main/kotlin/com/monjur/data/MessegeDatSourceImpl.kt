package com.monjur.data

import com.monjur.data.models.Messege
import org.litote.kmongo.coroutine.CoroutineDatabase

class MessegeDatSourceImpl(database:CoroutineDatabase) : MessegeDatSource {
    private val messageCollection=database.getCollection<Messege>()
    override suspend fun getAllMessage(): List<Messege> {
        return messageCollection.find()
            .descendingSort(Messege::timeStamp)
            .toList()
    }

    override suspend fun insertMessage(message: Messege) {
        messageCollection.insertOne(message)
    }
}