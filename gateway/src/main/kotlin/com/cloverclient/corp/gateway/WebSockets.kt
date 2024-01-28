package com.cloverclient.corp.gateway

import com.google.protobuf.GeneratedMessageV3
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf
import java.time.Duration

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
@OptIn(ExperimentalSerializationApi::class)
fun Application.configureWebSockets()
{
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(10)
        timeout = Duration.ofSeconds(10)
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(ProtoBuf)
    }
}

suspend fun ApplicationCall.respondProtoBuf(serializable: GeneratedMessageV3) = respondBytes(serializable.toByteArray())
