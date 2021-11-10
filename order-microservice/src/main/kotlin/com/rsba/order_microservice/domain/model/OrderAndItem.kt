package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class OrderAndItem(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val itemId: UUID,
)