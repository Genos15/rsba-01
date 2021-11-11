package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AddCategoryInOrderInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val categoryId: UUID,
    val itemCount: Int
)