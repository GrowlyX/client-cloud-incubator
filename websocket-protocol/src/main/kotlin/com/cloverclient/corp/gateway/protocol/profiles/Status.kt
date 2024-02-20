package com.cloverclient.corp.gateway.protocol.profiles

import kotlinx.serialization.Serializable

/**
 * @author GrowlyX
 * @since 2/19/2024
 */
@Serializable
data class Status(val server: String = "No Server")
