package com.rsba.tasks_microservice.domain.model

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class GanttData(
    @Serializable(with = UUIDSerializer::class) val TaskID: UUID,
    val TaskName: String,
    @Serializable(with = DateTimeSerializer::class) val StartDate: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val EndDate: OffsetDateTime? = null,
    val Duration: Float? = 0f,
    val Progress: Float? = 0f,
    @Serializable(with = UUIDSerializer::class) val Predecessor: UUID? = null,
    val subtasks: List<GanttData>? = emptyList(),
    val item: Item? = null,
    val operation: Operation? = null
)
