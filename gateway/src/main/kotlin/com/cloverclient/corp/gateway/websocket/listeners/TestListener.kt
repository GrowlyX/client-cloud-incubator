package com.cloverclient.corp.gateway.websocket.listeners

import com.cloverclient.corp.gateway.websocket.*
import kotlinx.serialization.Serializable
import org.jvnet.hk2.annotations.Service

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
@Service
class TestListener : Listener<TestListener.Data, TestListener.TestResult>
{
    @Serializable
    data class Data(val message: String)

    @Serializable
    data class TestResult(val value: String)

    override val typeParameters = Data::class to TestResult::class
    override suspend fun handle(context: WebSocketContext, data: Data): Result<TestResult>
    {
        if (data.message == "hors")
        {
            return IllegalStateException("Cannot accept hors")
                .failedResultOf()
        }

        return TestResult(value = "test").resultOf()
    }
}
