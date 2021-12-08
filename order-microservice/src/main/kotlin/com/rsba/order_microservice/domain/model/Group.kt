package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import java.time.OffsetDateTime

@Serializable
data class Group(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val priority: Int? = 0,
    val isAnalytic: Boolean? = null,
    val isStaging: Boolean? = null,
)