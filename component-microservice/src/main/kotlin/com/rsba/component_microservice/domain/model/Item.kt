package com.rsba.component_microservice.domain.model

import com.rsba.component_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Item(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val material: String? = null,
    val createdAt: String? = null,
    val editedAt: String? = null,
    @Serializable(with = UUIDSerializer::class) val creator: UUID? = null,
    val operations: List<Operation>? = listOf(),
    val category: CategoryOfItem? = null,
    val components: List<Item>? = listOf()
)
