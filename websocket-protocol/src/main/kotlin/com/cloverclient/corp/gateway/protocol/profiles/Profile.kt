package com.cloverclient.corp.gateway.protocol.profiles

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.util.*

/**
 * @author GrowlyX
 * @since 2/11/2024
 */
@Serializable
@DynamoDbBean
data class Profile(
    @get:DynamoDbPartitionKey
    var profileId: @Contextual UUID = UUID.randomUUID(),
    var username: String = ""
)
