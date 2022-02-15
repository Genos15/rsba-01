package com.rsba.tasks_microservice.data.dao

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.domain.format.ModelType
import com.rsba.tasks_microservice.domain.format.ModelTypeCase
import com.rsba.tasks_microservice.domain.model.GanttData
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.gantt_data)
data class GanttDataDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    @Serializable(with = UUIDSerializer::class) val TaskID: UUID,
    val TaskName: String,
    @Serializable(with = DateTimeSerializer::class) val StartDate: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val EndDate: OffsetDateTime? = null,
    val Duration: Float? = 0f,
    val Progress: Float? = 0f,
    @Serializable(with = UUIDSerializer::class) val Predecessor: UUID? = null,
    val subtasks: List<GanttData>? = emptyList()
) : AbstractModel() {

    val to: GanttData
        get() = GanttData(
            TaskID = TaskID,
            TaskName = TaskName,
            StartDate = StartDate,
            EndDate = EndDate,
            Duration = Duration,
            Progress = Progress,
            Predecessor = Predecessor,
            subtasks = subtasks
        )
}