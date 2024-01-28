package com.cloverclient.corp.core

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
fun Application.configureContentNegotiation()
{
    install(ContentNegotiation) {
        json(Json)
    }
}
