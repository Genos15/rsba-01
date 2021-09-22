package com.rsba.monitor_ms.domain.input

import com.rsba.monitor_ms.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AddCategoryInOrderInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val categoryId: UUID,
    val itemCount: Int
)