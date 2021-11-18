package com.rsba.order_microservice.configuration.deserializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId

class DateTimeSerializer : KSerializer<OffsetDateTime> {

    private val serializer = String.serializer()

    override val descriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        val datetime = LocalDateTime.parse(serializer.deserialize(decoder))
        return datetime.atZone(ZoneId.of("Europe/Moscow")).toOffsetDateTime()
    }

    override fun serialize(encoder: Encoder, value: OffsetDateTime) = try {
        serializer.serialize(encoder, (value as? OffsetDateTime?)?.toString() ?: "")
    } catch (e: Exception) {
        e.printStackTrace()
    }

}