package com.cloverclient.corp.core

import com.cloverclient.corp.core.inject.configureInject
import com.cloverclient.corp.core.serialization.configureContentNegotiation
import io.ktor.server.application.*

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
fun Application.coreModule()
{
    configureInject()
    configureContentNegotiation()
    configureAuthentication()
    configureCloudServices()
}
