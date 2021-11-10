package com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ItemInOrder(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    val itemIds: MutableList<String>? = mutableListOf()
)