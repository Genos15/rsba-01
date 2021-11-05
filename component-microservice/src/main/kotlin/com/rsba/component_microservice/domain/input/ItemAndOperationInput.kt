package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.configuration.scalar.BigDecimalSerializer
import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.*

@Serializable
class ItemAndOperationInput(
    @Serializable(with = UUIDSerializer::class) val itemId: UUID,
    @Serializable(with = UUIDSerializer::class) val operationId: UUID,
    @Serializable(with = BigDecimalSerializer::class) val estimatedTimeInHour: BigDecimal? = null,
)