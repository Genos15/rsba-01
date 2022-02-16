package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class Task(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val quantity: Float,
    val doneQuantity: Float,
    val estimatedTimeInHour: Float,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedEndDate: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    var operation: Operation? = null,
    var item: Item? = null,
    val departments: List<Department>? = emptyList(),
    val comments: List<Comment>? = emptyList(),
    val users: List<User>? = emptyList(),
    var order: Order? = null,
    val parameters: List<Parameter>? = emptyList(),
)
