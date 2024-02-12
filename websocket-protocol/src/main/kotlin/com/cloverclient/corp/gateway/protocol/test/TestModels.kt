package com.cloverclient.corp.gateway.protocol.test

import kotlinx.serialization.Serializable

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
@Serializable
data class TestRequest(val message: String)

@Serializable
data class TestResponse(val value: String)
