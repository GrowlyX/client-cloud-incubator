package com.cloverclient.corp.core

import io.ktor.server.application.*

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
fun Application.coreModule()
{
    configureContentNegotiation()
    configureAuthentication()
}
