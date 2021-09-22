package com.rsba.monitor_ms.domain.input

import com.rsba.monitor_ms.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CategoryInOrderInput(
    @Serializable(with = UUIDSerializer::class) val categoryId: UUID,
    val itemCount: Int? = null
)
