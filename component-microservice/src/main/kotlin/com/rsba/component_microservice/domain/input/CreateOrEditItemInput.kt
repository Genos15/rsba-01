package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateOrEditItemInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val material: String? = null,
    val operations: List<String>? = listOf(),
    @Serializable(with = UUIDSerializer::class) val categoryId: UUID? = null,
    val components: List<String>? = listOf(),
)