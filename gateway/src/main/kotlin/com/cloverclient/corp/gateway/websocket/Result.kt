package com.cloverclient.corp.gateway.websocket

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
data class Result<T>(
    val data: T?,
    private val exception: Throwable? = null
)
{
    fun isSuccess() = exception == null
    fun isFailure() = exception != null
}
