package com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class OrderTypeInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val isDefault: Boolean? = null,
)
