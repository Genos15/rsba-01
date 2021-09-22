package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ProgressionStep(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val priority: Int? = null,
    val rowIndex: Int,
    val isActive: Boolean = false,
    val isDone: Boolean = false
)
