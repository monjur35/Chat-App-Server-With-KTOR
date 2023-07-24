package com.monjur.di

import com.monjur.data.MessegeDatSource
import com.monjur.data.MessegeDatSourceImpl
import com.monjur.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val appModule= module {
    single {
       KMongo.createClient()
           .coroutine
           .getDatabase("CHAT_DB")

    }

    single<MessegeDatSource> {
        MessegeDatSourceImpl(get())
    }
    single {
        RoomController(get())
    }
}