package com.cloverclient.corp.gateway

import com.cloverclient.corp.core.idp.idpUser
import com.cloverclient.corp.core.inject.getAllServices
import com.cloverclient.corp.core.inject.serviceLocator
import com.cloverclient.corp.gateway.websocket.WebSocketContext
import com.cloverclient.corp.gateway.websocket.WebSocketError
import com.cloverclient.corp.gateway.websocket.lifecycle.ClientLifecycleListener
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.serializer
import java.nio.ByteBuffer
import java.util.*

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
fun Application.configureRouting()
{
    val logger = log
    routing {
        get("/health") {
            call.respondText("OK", status = HttpStatusCode.OK)
        }

        authenticate("account") {
            get("/authentication-test") {
                val principal = call.idpUser()
                call.respond("it works!! hi ${principal["email"]}")
            }

            val lifecycleListeners = serviceLocator.getAllServices<ClientLifecycleListener>()
            webSocket("/start") {
                val principal = call.idpUser()
                val session = object : WebSocketContext
                {
                    override val principal = principal
                    override val sessionId = UUID.randomUUID()
                }

                lifecycleListeners.forEach { it.connect(session) }

                try
                {
                    incoming.consumeEach {
                        val bytes = it.readBytes()
                        val code = bytes.firstOrNull()?.toInt()

                        if (code == null || it !is Frame.Binary) {
                            send(
                                constructWebSocketResponse(
                                    0x0, UUID.randomUUID(),
                                    WebSocketError("Invalid listener mapping")
                                )
                            )
                            return@consumeEach
                        }

                        val messageIdBytes = bytes.copyOfRange(1, 17)
                        val messageId = ByteBuffer.wrap(messageIdBytes)
                            .let { byteBuffer ->
                                UUID(byteBuffer.long, byteBuffer.long)
                            }

                        val mapping = websocketListeners[code]
                            ?: return@consumeEach run {
                                send(
                                    constructWebSocketResponse(
                                        0x0, messageId,
                                        WebSocketError("Invalid listener mapping")
                                    )
                                )
                            }

                        val jsonByteData = bytes.drop(17).toByteArray().inputStream()
                        val jsonData = Json.decodeFromStream(
                            mapping.typeParameters.first.serializer(),
                            jsonByteData
                        )

                        val response = mapping.handleTypeCasted(session, jsonData)
                        if (response.isFailure())
                        {
                            send(
                                constructWebSocketResponse(
                                    0x0, messageId,
                                    WebSocketError("Internal server error")
                                )
                            )
                            return@consumeEach
                        }

                        send(
                            constructWebSocketResponse(
                                code, messageId,
                                response.data
                            )
                        )
                    }
                } catch (ignored: ClosedReceiveChannelException)
                {
                    lifecycleListeners.forEach { it.disconnect(session) }
                } catch (exception: Throwable)
                {
                    lifecycleListeners.forEach { it.error(session) }
                    logger.error("WebSocket error", exception)
                }
            }
        }
    }
}
