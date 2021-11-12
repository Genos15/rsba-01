package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.component_microservice.domain.format.ModelType
import com.rsba.component_microservice.domain.format.ModelTypeCase
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.item_category)
data class ItemCategoryInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    private val children: List<String>? = emptyList(),
) : AbstractInput()