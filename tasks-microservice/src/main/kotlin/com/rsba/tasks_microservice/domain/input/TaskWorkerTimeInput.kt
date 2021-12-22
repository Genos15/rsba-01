package com.rsba.tasks_microservice.domain.input

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class TaskWorkerTimeInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    @Serializable(with = DateTimeSerializer::class) val startAt: OffsetDateTime,
) : AbstractInput()
