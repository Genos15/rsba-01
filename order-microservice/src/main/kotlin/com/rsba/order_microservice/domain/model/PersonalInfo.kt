package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class PersonalInfo(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val firstname: String,
    val lastname: String,
    val middlename: String? = null,
    @Serializable(with = DateTimeSerializer::class) val birthday: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
)
