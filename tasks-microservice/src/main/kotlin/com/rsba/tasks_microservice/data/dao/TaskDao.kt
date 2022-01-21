package com.rsba.tasks_microservice.data.dao

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.configuration.scalar.BigDecimalSerializer
import com.rsba.tasks_microservice.domain.format.ModelType
import com.rsba.tasks_microservice.domain.format.ModelTypeCase
import com.rsba.tasks_microservice.domain.model.Task
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.tasks)
data class TaskDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    @Serializable(with = BigDecimalSerializer::class) val quantity: BigDecimal? = BigDecimal.ONE,
    @Serializable(with = BigDecimalSerializer::class) val doneQuantity: BigDecimal? = BigDecimal.ZERO,
    @Serializable(with = BigDecimalSerializer::class) val estimatedTimeInHour: BigDecimal? = BigDecimal.ONE,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedEndDate: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val estimatedStartDate: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
) : AbstractModel() {

    val to: Task
        get() = Task(
            id = id,
            quantity = quantity,
            estimatedTimeInHour = estimatedTimeInHour,
            description = description,
            estimatedStartDate = estimatedStartDate,
            estimatedEndDate = estimatedEndDate,
            createdAt = createdAt,
            editedAt = editedAt,
            doneQuantity = doneQuantity
        )
}