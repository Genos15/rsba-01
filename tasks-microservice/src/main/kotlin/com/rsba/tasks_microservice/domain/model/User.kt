package com.rsba.tasks_microservice.domain.model

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class User(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val firstname: String,
    val lastname: String,
    val middlename: String? = null,
    val workload: Float = 0f,
    @Serializable(with = DateTimeSerializer::class) val estimatedStartDate: OffsetDateTime? = null,
    val activity: List<UserActivity>? = emptyList()
)