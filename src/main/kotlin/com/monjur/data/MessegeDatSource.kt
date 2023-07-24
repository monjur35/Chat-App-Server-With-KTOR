package com.monjur.data

import com.monjur.data.models.Messege

interface MessegeDatSource {
    suspend fun getAllMessage():List<Messege>

    suspend fun insertMessage(message: Messege)
}