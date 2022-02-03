package com.rsba.tasks_microservice.domain.model

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*
import java.time.OffsetDateTime

@Serializable
data class Worklog(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val title: String? = null,
    val contentTitle: String? = null,
    val content: String? = null,
    val details: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
)
