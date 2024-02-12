package com.cloverclient.corp.gateway

import com.cloverclient.corp.core.inject.serviceLocator
import com.cloverclient.corp.gateway.websocket.Listener
import com.cloverclient.corp.gateway.websocket.Mapped
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json
import java.time.Duration

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
val websocketListeners = mutableMapOf<Int, Listener<*, *>>()
fun Application.configureWebSockets()
{
    serviceLocator
        .getAllServices<Listener<*, *>>(Mapped::class.java)
        .forEach {
            val mapping = it.javaClass.getAnnotation(Mapped::class.java)
            websocketListeners[mapping.value] = it
        }

    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(10)
        timeout = Duration.ofSeconds(10)
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }
}

