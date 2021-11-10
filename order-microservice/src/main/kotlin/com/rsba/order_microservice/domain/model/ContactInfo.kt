package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class ContactInfo(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val value: String,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val streetAddress: String? = null,
    val postalCode: String? = null,
    val city: String? = null,
    val stateProvince: String? = null
)
