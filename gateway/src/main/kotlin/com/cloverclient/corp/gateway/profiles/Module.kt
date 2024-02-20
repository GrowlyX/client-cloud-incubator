package com.cloverclient.corp.gateway.profiles

import io.ktor.server.application.*

/**
 * @author GrowlyX
 * @since 2/19/2024
 */
fun Application.profilesModule()
{
    configureProfilesDatabase()
}
