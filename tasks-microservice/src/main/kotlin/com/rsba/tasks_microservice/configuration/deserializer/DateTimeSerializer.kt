package com.rsba.parameters_microservice.configuration.deserializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId

import java.time.ZonedDateTime


class DateTimeSerializer : KSerializer<OffsetDateTime?> {

    private val serializer = String.serializer()

    override val descriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): OffsetDateTime? = try {
        val datetime = LocalDateTime.parse(serializer.deserialize(decoder))
        datetime.atZone(ZoneId.of("Europe/Moscow")).toOffsetDateTime()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    override fun serialize(encoder: Encoder, value: OffsetDateTime?) = try {
        serializer.serialize(encoder, value?.toString() ?: "")
    } catch (e: Exception) {
        e.printStackTrace()
    }

}