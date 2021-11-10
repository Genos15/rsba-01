package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class Worklog(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val contentTitle: String,
    val content: String,
    val details: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    var actor: User? = null,
)
