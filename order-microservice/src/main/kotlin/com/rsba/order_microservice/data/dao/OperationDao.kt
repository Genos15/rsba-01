package com.rsba.order_microservice.data.dao

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.Operation
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.operations)
data class OperationDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val name: String,
    val description: String? = null,
    val move: String? = null,
    val estimatedTimeInHour: Float? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val priority: Int? = null,
) : AbstractModel() {

    val to: Operation
        get() = Operation(
            id = id,
            name = name,
            description = description,
            createdAt = createdAt,
            editedAt = editedAt,
            estimatedTimeInHour = estimatedTimeInHour,
            move = move,
            priority = priority,
        )
}