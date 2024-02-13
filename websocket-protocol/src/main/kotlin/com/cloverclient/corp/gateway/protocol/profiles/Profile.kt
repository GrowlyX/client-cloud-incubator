package com.cloverclient.corp.gateway.protocol.profiles

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.util.*

/**
 * @author GrowlyX
 * @since 2/11/2024
 */
@DynamoDbBean
data class Profile(
    @get:DynamoDbAttribute("clover_profile_id")
    @get:DynamoDbPartitionKey
    val cloverId: UUID
)
