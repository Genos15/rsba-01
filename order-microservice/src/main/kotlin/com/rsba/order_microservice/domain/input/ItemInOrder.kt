package com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class ItemInOrder(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    val itemIds: MutableList<String>? = mutableListOf()
)