package com.cloverclient.corp.gateway

import com.cloverclient.corp.gateway.protocol.constructWebSocketReqResp
import com.cloverclient.corp.gateway.protocol.deconstructWebSocketResponse
import com.cloverclient.corp.gateway.protocol.test.TestRequest
import com.cloverclient.corp.gateway.protocol.test.TestResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.util.*
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
            client.webSocket("wss://gateway.cloverclientdev.com/start", request = {
                headers {
                    set(
                        "Cookie",
                        "CloverGatewayProdSession-0=${AuthenticatedUnitTests.CookieComponent0}; CloverGatewayProdSession-1=${AuthenticatedUnitTests.CookieComponent1}"
                    )
                }
            }, block = {
                val constructed = constructWebSocketReqResp(
                    mappingCode = 0x1,
                    messageId = UUID.randomUUID(),
                    TestRequest(message = "hey")
                )

                send(constructed)

                val response = incoming.receive()
                val deconstructed = deconstructWebSocketResponse<TestResponse>(response.readBytes())
                println(deconstructed.response.value)
            })
        }
    }
}
