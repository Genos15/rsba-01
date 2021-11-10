package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class OrderType(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val isDefault: Boolean
)