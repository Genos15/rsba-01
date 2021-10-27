package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Parameter(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val value: String,
    val type: ParameterType? = null,
    val potentialValues: List<String>? = emptyList()
)