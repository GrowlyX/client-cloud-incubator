package com.cloverclient.corp.gateway.websocket

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
fun <T : Any?> T.resultOf() = Result(this, null)
fun <T : Any?> Throwable.failedResultOf() = Result(null as T?, this)
