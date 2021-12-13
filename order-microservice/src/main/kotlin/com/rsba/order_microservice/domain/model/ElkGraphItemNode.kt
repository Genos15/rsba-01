package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

data class ElkGraphItemNode(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    override val height: Int,
    override val width: Int,
    val node: Item
) : ElkGraphNode(id = id, height = height, width = width)
