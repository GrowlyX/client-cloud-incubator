package com.cloverclient.corp.core.services

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
object Database
{
    private lateinit var dynamoDbClient: DynamoDbEnhancedAsyncClient

    fun client() = dynamoDbClient
    fun configureDynamoClient(region: String)
    {
        val client = DynamoDbAsyncClient.builder()
            .region(Region.of(region))
            .build()

        dynamoDbClient = DynamoDbEnhancedAsyncClient.builder()
            .dynamoDbClient(client)
            .build()
    }
}
