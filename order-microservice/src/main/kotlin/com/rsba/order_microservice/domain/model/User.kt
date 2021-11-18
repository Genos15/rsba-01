package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class User(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val lang: String? = null,
    val name: String? = null,
    val personalInfo: PersonalInfo? = null,
    val contactInfo: List<ContactInfo>? = emptyList(),
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
)
