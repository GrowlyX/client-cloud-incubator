package com.cloverclient.corp.gateway.websocket.listeners

import com.cloverclient.corp.gateway.protocol.test.TestRequest
import com.cloverclient.corp.gateway.protocol.test.TestResponse
import com.cloverclient.corp.gateway.websocket.*
import org.jvnet.hk2.annotations.Service

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
@Service
@Mapped(0x1)
class TestListener : Listener<TestRequest, TestResponse>
{
    override val typeParameters = TestRequest::class to TestResponse::class
    override suspend fun handle(context: WebSocketContext, data: TestRequest): Result<TestResponse>
    {
        if (data.message == "hors")
        {
            return IllegalStateException("Cannot accept hors")
                .failedResultOf()
        }

        return TestResponse(value = "test").resultOf()
    }
}
