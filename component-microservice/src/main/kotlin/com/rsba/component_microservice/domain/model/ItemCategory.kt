package com.rsba.component_microservice.domain.model

import com.example.ticketApp.deserializer.DateTimeSerializer
import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class ItemCategory(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val children: List<ItemCategory>? = emptyList(),
    val subItems: List<Item>? = emptyList(),
)
