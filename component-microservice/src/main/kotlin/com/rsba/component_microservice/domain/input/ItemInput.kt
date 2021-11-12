package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.component_microservice.domain.format.ModelType
import com.rsba.component_microservice.domain.format.ModelTypeCase
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.items)
data class ItemInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val material: String? = null,
    val operations: List<String>? = emptyList(),
    @Serializable(with = UUIDSerializer::class) val itemCategoryId: UUID? = null,
    val components: List<String>? = emptyList(),
    val technologies: List<String>? = emptyList(),
) : AbstractInput()