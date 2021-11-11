package com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class TaskInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val orderId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val itemId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val operationId: UUID? = null,
    val quantity: Float? = null,
    val estimatedTimeInHour: Float? = null,
    val description: String? = null,
)
