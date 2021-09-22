package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class FindTaskAttachedToTechnologyInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    val technologiesIds: List<String>? = emptyList(),
)