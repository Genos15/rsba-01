package com.rsba.file_microservice.domain.model

import com.rsba.file_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Operation(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val move: String? = null,
    val estimatedTimeInHour: Float? = null,
    val createdAt: String? = null,
    val editedAt: String? = null,
    @Serializable(with = UUIDSerializer::class) val creator: UUID? = null
)