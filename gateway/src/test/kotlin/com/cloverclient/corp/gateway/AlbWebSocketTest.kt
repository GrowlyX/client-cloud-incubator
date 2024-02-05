package com.cloverclient.corp.gateway

import com.cloverclient.corp.gateway.models.Simple1
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test

class AlbWebSocketTest
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
            client.webSocket("wss://gateway.corp.cloverclientdev.com/start", request = {
                headers {
                    set(
                        "Cookie",
                        "CloverGatewayProdSession-0=${AuthenticatedUnitTests.CookieComponent0}; CloverGatewayProdSession-1=${AuthenticatedUnitTests.CookieComponent1}"
                    )
                }
            }, block = {
                sendSerialized(
                    Simple1(
                        testMessage = "hors"
                    )
                )

                println(receiveDeserialized<Simple1>())
            })
        }
    }
}
