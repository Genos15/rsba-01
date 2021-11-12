package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.configuration.scalar.BigDecimalSerializer
import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.*

@Serializable
data class OperationInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val move: String? = null,
    @Serializable(with = BigDecimalSerializer::class) val estimatedTimeInHour: BigDecimal? = null,
    val departments: List<String>? = emptyList()
): AbstractInput()

