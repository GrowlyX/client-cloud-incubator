package com.cloverclient.corp.gateway.protocol.friendship

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey
import java.util.UUID

/**
 * @author GrowlyX
 * @since 2/19/2024
 */
@Serializable
data class Friendship(
    @get:DynamoDbPartitionKey
    var friendshipId: @Contextual UUID = UUID.randomUUID(),
    var profileIdOne: @Contextual UUID,
    var profileIdTwo: @Contextual UUID
)
