package com.rsba.tasks_microservice.domain.model

import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Order(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val referenceNumber: String,
    val description: String? = null,
)
