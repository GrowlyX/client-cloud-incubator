package com.cloverclient.corp.core.services

import com.cloverclient.corp.core.serialization.json
import kotlinx.serialization.encodeToString
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.util.concurrent.CompletableFuture

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
            .credentialsProvider(InstanceProfileCredentialsProvider.create())
            .build()

        dynamoDbClient = DynamoDbEnhancedAsyncClient.builder()
            .dynamoDbClient(client)
            .build()
    }
}

inline fun <reified T> CompletableFuture<EnhancedDocument>.convertToModel(): CompletableFuture<T?> =
    thenApply {
        json.decodeFromString<T>(it.toJson())
    }

inline fun <reified T> Any.convertToDocument(): EnhancedDocument = EnhancedDocument.fromJson(
    json.encodeToString(this as T)
)
