package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class DetailItemInOrder(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val itemId: UUID,
    @Serializable(with = DateTimeSerializer::class) val addedInOrderAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    var whoAdded: User? = null,
    val technologies: List<Technology>? = emptyList(),
    val totalNumberOfTasks: Int,
    val totalNumberOfTasksDone: Int
)
