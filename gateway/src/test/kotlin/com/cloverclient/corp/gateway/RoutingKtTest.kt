package com.cloverclient.corp.gateway

import com.cloverclient.corp.gateway.models.Simple1
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test

class RoutingKtTest
{
    @Test
    fun testWebsocketApiV1Start()
    {
        val client = HttpClient(CIO) {
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
        }

        runBlocking {
            client.webSocket(
                method = HttpMethod.Get,
                host = "127.0.0.1",
                port = 8080,
                path = "/start"
            ) {
                sendSerialized(
                    Simple1(
                        testMessage = "hors"
                    )
                )

                println(receiveDeserialized<Simple1>())
            }
        }
    }
}
