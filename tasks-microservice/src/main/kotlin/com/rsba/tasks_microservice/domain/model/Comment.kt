package com.rsba.tasks_microservice.domain.model

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class Comment(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val comment: String,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
    private val reporter: User? = null
)
