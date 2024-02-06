package com.cloverclient.corp.gateway.websocket.lifecycle

import com.cloverclient.corp.gateway.websocket.WebSocketContext
import org.jvnet.hk2.annotations.Contract

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
@Contract
interface ClientLifecycleListener
{
    fun connect(context: WebSocketContext)
    {

    }

    fun disconnect(context: WebSocketContext)
    {

    }

    fun error(context: WebSocketContext)
    {

    }
}
