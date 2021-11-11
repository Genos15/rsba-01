package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class ElkGraphNode(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val height: Int,
    val width: Int,
    val payload: Item.GraphItem
)