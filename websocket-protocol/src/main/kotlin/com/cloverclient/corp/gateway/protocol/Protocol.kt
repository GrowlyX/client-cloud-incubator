package com.cloverclient.corp.gateway.protocol

import com.cloverclient.corp.core.serialization.json
import kotlinx.serialization.*
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.*
import kotlin.reflect.KClass

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
fun <T : Any> constructWebSocketReqResp(
    mappingCode: Int, messageId: UUID, data: T,
    serializer: KSerializer<*> = data::class.serializer()
): ByteArray
{
    val messageIdBytes = ByteBuffer
        .allocate(16)
        .apply {
            putLong(messageId.mostSignificantBits)
            putLong(messageId.leastSignificantBits)
        }
        .array()


    val dataOutputStream = ByteArrayOutputStream()
    json.encodeToStream(serializer as KSerializer<T>, data, dataOutputStream)
    val dataOutputBytes = dataOutputStream.toByteArray()

    return ByteBuffer
        .allocate(1 + messageIdBytes.size + dataOutputBytes.size)
        .apply {
            put(mappingCode.toByte())
            put(messageIdBytes)
            put(dataOutputBytes)
        }
        .array()
}

data class WebSocketResponse<T>(
    val mappingCode: Int,
    val messageId: UUID,
    val response: T
)

@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
inline fun <reified T : Any> deconstructWebSocketResponse(
    bytes: ByteArray,
    customSerializerClass: KClass<T> = T::class
): WebSocketResponse<T>
{
    val code = bytes.firstOrNull()?.toInt()
        ?: throw IllegalStateException(
            "Websocket response contains no mapping code"
        )

    val messageIdBytes = bytes.copyOfRange(1, 17)
    val messageId = ByteBuffer.wrap(messageIdBytes)
        .let { byteBuffer ->
            UUID(byteBuffer.long, byteBuffer.long)
        }

    val jsonByteData = bytes.drop(17)
        .toByteArray().inputStream()

    val jsonData = runCatching {
        json.decodeFromStream(customSerializerClass.serializer(), jsonByteData)
    }.getOrNull()
        ?: throw IllegalStateException(
            "Websocket response contains malformed json data"
        )

    return WebSocketResponse(mappingCode = code, messageId = messageId, response = jsonData)
}
