package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class ItemAndOperationInput(
    @Serializable(with = UUIDSerializer::class) val itemId: UUID,
    @Serializable(with = UUIDSerializer::class) val operationId: UUID,
    val estimatedTimeInHour: Float? = 1.0f
)