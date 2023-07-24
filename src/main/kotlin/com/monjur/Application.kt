package com.monjur

import com.monjur.di.appModule
import com.monjur.plugins.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    install(Koin){
        modules(appModule)
    }

    configureSockets()
    configureSerialization()
    configureSecurity()
    configureRouting()
    configureMonitoring()
}


