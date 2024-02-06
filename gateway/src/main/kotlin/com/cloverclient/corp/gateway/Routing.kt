package com.cloverclient.corp.gateway

import com.cloverclient.corp.core.idp.idpUser
import com.cloverclient.corp.core.inject.getAllServices
import com.cloverclient.corp.core.inject.serviceLocator
import com.cloverclient.corp.gateway.models.Simple1
import com.cloverclient.corp.gateway.websocket.WebSocketContext
import com.cloverclient.corp.gateway.websocket.lifecycle.ClientLifecycleListener
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.util.*

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
fun Application.configureRouting()
{
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
                    while (true)
                    {
                        val receiveBytes = receiveDeserialized<>()

                        val simple1 = receiveDeserialized<Simple1>()
                        println("Simple 1: ${simple1.testMessage}")

                        sendSerialized(Simple1(
                            testMessage = "hi ${principal["email"]}"
                        ))
                    }
                } catch (ignored: ClosedReceiveChannelException)
                {
                    lifecycleListeners.forEach { it.disconnect(session) }
                } catch (exception: Throwable)
                {
                    lifecycleListeners.forEach { it.error(session) }

                    println("onError ${closeReason.await()}")
                    exception.printStackTrace()
                }
            }
        }
    }
}
