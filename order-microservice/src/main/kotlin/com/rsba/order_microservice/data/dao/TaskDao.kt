package com.rsba.order_microservice.data.dao

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.Task
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.tasks)
data class TaskDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val quantity: Float,
    val doneQuantity: Float? = 0f,
    val estimatedTimeInHour: Float,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedEndDate: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
) : AbstractModel() {

    val to: Task
        get() = Task(
            id = id,
            estimatedTimeInHour = estimatedTimeInHour,
            description = description,
            createdAt = createdAt,
            editedAt = editedAt,
            quantity = quantity,
            estimatedEndDate = estimatedEndDate,
            doneQuantity = doneQuantity ?: 0f
        )
}
