package com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class FindItemAttachedToTechnologyInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    val technologiesIds: List<String>? = emptyList(),
)