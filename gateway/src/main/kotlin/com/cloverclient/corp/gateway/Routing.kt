package com.cloverclient.corp.gateway

import com.cloverclient.corp.gateway.models.Simple1
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
fun Application.configureRouting()
{
    routing {
        get("/health") {
            call.respond(mapOf(
                "healthy" to "true"
            ))
        }

        authenticate("account") {
            get("/authentication-test") {
                call.respond("it works!!")
            }

            webSocket("/start") {
                try
                {
                    while (true)
                    {
                        val simple1 = receiveDeserialized<Simple1>()
                        println("Simple 1: ${simple1.testMessage}")

                        sendSerialized(Simple1(
                            testMessage = "response"
                        ))
                    }
                } catch (ignored: ClosedReceiveChannelException)
                {
                    println("onClose ${closeReason.await()}")
                } catch (e: Throwable)
                {
                    println("onError ${closeReason.await()}")
                    e.printStackTrace()
                }
            }
        }
    }
}
