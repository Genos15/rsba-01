package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ItemAndItemInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val orderId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val parentId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val childId: UUID? = null,
    val quantity: Float? = 0f
)