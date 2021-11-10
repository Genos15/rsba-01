package com.rsba.component_microservice.domain.model

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

data class ElkGraphItemCategoryNode(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    override val height: Int,
    override val width: Int,
    val node: ItemCategory
) : ElkGraphNode(id = id, height = height, width = width)
