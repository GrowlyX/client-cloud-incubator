package com.cloverclient.corp.gateway.protocol.friendship

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

/**
 * @author GrowlyX
 * @since 2/19/2024
 */
@Serializable
data class Friendship(
    var friendshipId: @Contextual UUID = UUID.randomUUID(),
    var profileIdOne: @Contextual UUID,
    var profileIdTwo: @Contextual UUID
)
