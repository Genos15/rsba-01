package com.rsba.tasks_microservice.domain.model

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class UserActivity(
    @Serializable(with = DateTimeSerializer::class) val date: OffsetDateTime? = null,
    val count: Int,
)