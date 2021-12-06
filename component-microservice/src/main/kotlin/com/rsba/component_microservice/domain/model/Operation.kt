package com.rsba.component_microservice.domain.model

import com.example.ticketApp.deserializer.DateTimeSerializer
import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class Operation(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val move: String? = null,
    val estimatedTimeInHour: Float? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    @Serializable(with = UUIDSerializer::class) val creator: UUID? = null,
    val departments: List<Group>? = listOf(),
    val priority: Int? = null,
)