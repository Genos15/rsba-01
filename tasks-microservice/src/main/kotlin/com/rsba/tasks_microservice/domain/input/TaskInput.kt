package com.rsba.tasks_microservice.domain.input

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.configuration.scalar.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class TaskInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID? = null,
    @Serializable(with = BigDecimalSerializer::class) val quantity: BigDecimal? = null,
    @Serializable(with = BigDecimalSerializer::class) val estimatedTimeInHour: BigDecimal? = null,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedEndDate: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedStartDate: OffsetDateTime? = null,
    @Serializable(with = UUIDSerializer::class) val workcenterId: UUID? = null,
    val users: List<String>? = emptyList(),
) : AbstractInput()