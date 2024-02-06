package com.cloverclient.corp.gateway.websocket

import kotlinx.serialization.Serializable

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
@Serializable
data class WebSocketError(
    val message: String
)
