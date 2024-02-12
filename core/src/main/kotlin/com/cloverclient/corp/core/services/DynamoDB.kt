package com.cloverclient.corp.core.services

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
object Database
{
    private lateinit var dynamoDbClient: DynamoDbEnhancedClient

    fun client() = dynamoDbClient
    fun configureDynamoClient(region: String)
    {
        val client = DynamoDbClient.builder()
            .region(Region.of(region))
            .build()

        dynamoDbClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(client)
            .build()
    }
}
