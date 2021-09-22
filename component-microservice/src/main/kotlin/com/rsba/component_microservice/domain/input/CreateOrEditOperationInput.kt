package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateOrEditOperationInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val move: String? = null,
    val estimatedTimeInHour: Float? = 1.0f,
    val departments: List<String>? = listOf()
)
