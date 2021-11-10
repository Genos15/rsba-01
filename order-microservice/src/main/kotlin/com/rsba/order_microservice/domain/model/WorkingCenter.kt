package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class WorkingCenter(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val users: List<User>? = emptyList(),
    val managers: List<User>? = emptyList(),
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val name: String,
    val description: String? = null,
    @Serializable(with = UUIDSerializer::class)  val taskId: UUID
)
