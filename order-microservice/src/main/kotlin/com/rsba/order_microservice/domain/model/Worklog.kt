package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class Worklog(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val title: String? = null,
    val contentTitle: String? = null,
    val content: String? = null,
    val details: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
)
