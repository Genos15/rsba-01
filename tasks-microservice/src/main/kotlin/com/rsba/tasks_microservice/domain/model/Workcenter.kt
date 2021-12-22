package com.rsba.tasks_microservice.domain.model

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class Workcenter(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
    val workload: Float = 0f,
)
