package com.rsba.component_microservice.configuration.deserializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = UUID::class)
class ListUUIDSerializer : KSerializer<List<UUID>> {

    private val serializer = ListSerializer(UUIDSerializer())

    override val descriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): List<UUID> =
        serializer.deserialize(decoder)

    override fun serialize(encoder: Encoder, value: List<UUID>) =
        serializer.serialize(encoder, value)
}