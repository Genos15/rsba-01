package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class EditItemInOrderInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val itemId: UUID,
    val quantity: Float? = 0f
)