package com.cloverclient.corp.gateway

import io.ktor.serialization.kotlinx.protobuf.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
@OptIn(ExperimentalSerializationApi::class)
fun Application.configureContentNegotiation()
{
    install(ContentNegotiation) {
        protobuf()
    }
}
