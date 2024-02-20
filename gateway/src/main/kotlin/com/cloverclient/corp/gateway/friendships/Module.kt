package com.cloverclient.corp.gateway.friendships

import io.ktor.server.application.*

/**
 * @author GrowlyX
 * @since 2/20/2024
 */
fun Application.friendshipsModule()
{
    configureFriendshipsDatabase()
}
