package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

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
