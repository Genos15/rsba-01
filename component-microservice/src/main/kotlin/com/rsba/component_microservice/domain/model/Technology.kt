package com.rsba.component_microservice.domain.model

import com.rsba.component_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Technology(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val createdAt: String? = null,
    val editedAt: String? = null,
    val operations: List<Operation>? = emptyList()
)