package com.cloverclient.corp.gateway.protocol.profiles

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

/**
 * @author GrowlyX
 * @since 2/11/2024
 */
@Serializable
data class Profile(
    val profileId: @Contextual UUID = UUID.randomUUID(),
    var username: String,
    var role: Role = Role.Owner,
    var status: Status = Status()
)
