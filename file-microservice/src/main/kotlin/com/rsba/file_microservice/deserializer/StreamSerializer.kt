package com.rsba.file_microservice.deserializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.springframework.util.SerializationUtils
import java.io.InputStream

class StreamSerializer : KSerializer<InputStream> {

    private val serializer = ByteArraySerializer()

    override val descriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): InputStream =
        SerializationUtils.deserialize(serializer.deserialize(decoder)) as InputStream

    override fun serialize(encoder: Encoder, value: InputStream) =
        serializer.serialize(encoder, SerializationUtils.serialize(value)!!)
}