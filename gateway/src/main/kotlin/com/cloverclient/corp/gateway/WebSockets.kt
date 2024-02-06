package com.cloverclient.corp.gateway

import com.cloverclient.corp.core.inject.serviceLocator
import com.cloverclient.corp.gateway.websocket.Listener
import com.cloverclient.corp.gateway.websocket.Mapped
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.time.Duration
import java.util.*

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

@OptIn(ExperimentalSerializationApi::class)
fun constructWebSocketResponse(mappingCode: Int, messageId: UUID, data: Any): ByteArray
{
    val messageIdBytes = ByteBuffer
        .allocate(16)
        .apply {
            putLong(messageId.mostSignificantBits)
            putLong(messageId.leastSignificantBits)
        }
        .array()

    val mappingCodeBytes = byteArrayOf(mappingCode.toByte())

    val dataOutputStream = ByteArrayOutputStream()
    Json.encodeToStream(data, dataOutputStream)
    val dataOutputBytes = dataOutputStream.toByteArray()

    return ByteBuffer
        .allocate(mappingCodeBytes.size + messageIdBytes.size + dataOutputBytes.size)
        .apply {
            put(mappingCodeBytes)
            put(messageIdBytes)
            put(dataOutputBytes)
        }
        .array()
}
