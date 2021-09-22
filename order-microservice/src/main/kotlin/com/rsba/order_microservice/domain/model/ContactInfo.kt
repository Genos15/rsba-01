package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.deserializer.DateTimeSerializer
import com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class ContactInfo(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val value: String,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val streetAddress: String,
    val postalCode: String,
    val city: String,
    val stateProvince: String
)
