package com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ItemInOrderInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val itemId: UUID
)