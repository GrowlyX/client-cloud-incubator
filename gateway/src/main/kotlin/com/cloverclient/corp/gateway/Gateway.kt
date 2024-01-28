package com.cloverclient.corp.gateway

import io.ktor.server.application.*

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
fun Application.gatewayModule()
{
    configureWebSockets()
    configureRouting()
}
