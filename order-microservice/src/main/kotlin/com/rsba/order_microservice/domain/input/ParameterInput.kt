package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.model.ParameterType
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ParameterInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val value: String? = null,
    val type: ParameterType? = null,
    @Serializable(with = UUIDSerializer::class) val hostId: UUID
)