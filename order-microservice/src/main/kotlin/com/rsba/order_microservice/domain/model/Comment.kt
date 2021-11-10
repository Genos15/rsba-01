package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class Comment(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val comment: String,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val actor: User? = null
)
