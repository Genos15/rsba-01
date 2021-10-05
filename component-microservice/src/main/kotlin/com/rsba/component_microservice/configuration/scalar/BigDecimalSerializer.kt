package com.rsba.component_microservice.configuration.scalar

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal

class BigDecimalSerializer : KSerializer<BigDecimal?> {

    private val serializer = Double.serializer()

    override val descriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): BigDecimal? = try {
        (serializer.deserialize(decoder)).let { BigDecimal.valueOf(it) }
    } catch (e: Exception) {
        e.printStackTrace()
        BigDecimal.valueOf(1)
    }

    override fun serialize(encoder: Encoder, value: BigDecimal?) = try {
        serializer.serialize(encoder, value?.toDouble() ?: 0.0)
    } catch (e: Exception) {
        e.printStackTrace()
    }

}