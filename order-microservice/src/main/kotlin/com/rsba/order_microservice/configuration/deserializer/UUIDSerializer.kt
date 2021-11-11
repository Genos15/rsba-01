package com.rsba.order_microservice.configuration.deserializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = UUID::class)
class UUIDSerializer : KSerializer<UUID> {
    private val serializer = String.serializer()

    override val descriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): UUID =
        UUID.fromString(serializer.deserialize(decoder))

    override fun serialize(encoder: Encoder, value: UUID) =
        serializer.serialize(encoder, value.toString())
}