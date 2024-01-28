package com.cloverclient.corp.core.services

import aws.sdk.kotlin.runtime.auth.credentials.EnvironmentCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import kotlin.time.Duration.Companion.seconds

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
object Database
{
    private lateinit var dynamoDbClient: DynamoDbClient

    fun client() = dynamoDbClient
    fun configureDynamoClient(region: String)
    {
        dynamoDbClient = DynamoDbClient {
            this.region = region
            credentialsProvider = EnvironmentCredentialsProvider()

            httpClient {
                maxConcurrency = 64u
                connectTimeout = 10.seconds
            }

            retryStrategy {
                maxAttempts = 2
            }
        }
    }
}
