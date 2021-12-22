package com.rsba.tasks_microservice.data.dao

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.domain.format.ModelType
import com.rsba.tasks_microservice.domain.format.ModelTypeCase
import com.rsba.tasks_microservice.domain.model.Workcenter
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.workcenters)
data class WorkcenterDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val name: String,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
    val workload: Float = 0f,
) : AbstractModel() {

    val to: Workcenter
        get() = Workcenter(
            id = id,
            name = name,
            description = description,
            createdAt = createdAt,
            editedAt = editedAt,
            workload = workload
        )
}