package com.cloverclient.corp.core

import com.cloverclient.corp.core.services.Database
import io.ktor.server.application.*

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
fun Application.configureCloudServices()
{
    val region = environment.config.property("aws.region").getString()
    if (region != "__local__")
    {
        Database.configureDynamoClient(region)
    }
}
