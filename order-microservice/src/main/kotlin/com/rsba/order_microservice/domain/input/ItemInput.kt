package com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.configuration.scalar.BigDecimalSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import java.math.BigDecimal

@Serializable
@ModelType(_class = ModelTypeCase.items)
data class ItemInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    @Serializable(with = BigDecimalSerializer::class) val quantity: BigDecimal? = null,
    val name: String? = null,
    val description: String? = null,
    val material: String? = null
) : AbstractInput()