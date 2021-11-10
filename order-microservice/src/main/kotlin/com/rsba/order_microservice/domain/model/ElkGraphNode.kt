package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ElkGraphNode(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val height: Int,
    val width: Int,
    val payload: Item.GraphItem
)