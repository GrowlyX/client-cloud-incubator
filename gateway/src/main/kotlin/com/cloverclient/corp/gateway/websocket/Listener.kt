package com.cloverclient.corp.gateway.websocket

import org.jvnet.hk2.annotations.Contract
import kotlin.reflect.KClass

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
@Contract
interface Listener<I : Any, O : Any>
{
    val typeParameters: Pair<KClass<I>, KClass<O>>
    suspend fun handle(context: WebSocketContext, data: I): Result<O>

    suspend fun handleTypeCasted(context: WebSocketContext, data: Any): Result<O> = handle(context, data as I)
}
