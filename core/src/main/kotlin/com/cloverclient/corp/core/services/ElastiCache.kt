package com.cloverclient.corp.core.services

import aws.sdk.kotlin.runtime.auth.credentials.EnvironmentCredentialsProvider
import aws.sdk.kotlin.services.elasticache.ElastiCacheClient
import kotlin.time.Duration.Companion.seconds

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
object Cache
{
    private lateinit var elastiCacheClient: ElastiCacheClient

    fun client() = elastiCacheClient
    fun configureElastiCacheClient(region: String)
    {
        elastiCacheClient = ElastiCacheClient {
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
