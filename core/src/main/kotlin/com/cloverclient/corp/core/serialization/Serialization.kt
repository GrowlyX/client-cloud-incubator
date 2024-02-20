package com.cloverclient.corp.core.serialization

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.util.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.Instant
import java.util.*

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    serializersModule = SerializersModule {
        contextual(UUID::class, UUIDSerializer)
        contextual(Instant::class, InstantSerializer)
    }
    encodeDefaults = true
    decodeEnumsCaseInsensitive = true
}

fun Application.configureContentNegotiation()
{
    install(ContentNegotiation) {
        json(json)
    }
}

object UUIDSerializer : KSerializer<UUID>
{
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("UUIDSerializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UUID =
        UUID.fromString(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: UUID)
    {
        encoder.encodeString(value.toString())
    }
}

object InstantSerializer : KSerializer<Instant>
{
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("InstantSerializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Instant =
        Instant.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Instant)
    {
        encoder.encodeString(value.toString())
    }
}
