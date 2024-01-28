package com.cloverclient.corp.gateway

import build.buf.gen.com.cloverclient.corp.v1.Simple1
import io.ktor.server.application.*
import io.ktor.server.routing.*

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
fun Application.configureRouting()
{
    routing {
        get("/testing") {
            call.respondProtoBuf(
                Simple1
                    .newBuilder()
                    .setAString("hors")
                    .addARepeatedString("hors1")
                    .build()
            )
        }
    }
}
