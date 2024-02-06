package com.cloverclient.corp.gateway.websocket

import com.cloverclient.corp.core.idp.IdpUser
import java.util.UUID

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
interface WebSocketContext
{
    val principal: IdpUser
    val sessionId: UUID
}
