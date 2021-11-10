package com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AddCategoryInOrderInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val categoryId: UUID,
    val itemCount: Int
)