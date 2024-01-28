package com.cloverclient.corp.gateway

import io.ktor.server.application.*
import io.ktor.server.netty.*

/**
 * @author GrowlyX
 * @since 1/27/2024
 */
fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module()
{
    configureContentNegotiation()
    configureAuthentication()
    configureWebSockets()
    configureRouting()

    println("started application")
}
