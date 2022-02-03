package com.rsba.tasks_microservice.domain.model

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.configuration.scalar.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class Task(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    @Serializable(with = BigDecimalSerializer::class) val quantity: BigDecimal? = BigDecimal.ONE,
    @Serializable(with = BigDecimalSerializer::class) val doneQuantity: BigDecimal? = BigDecimal.ZERO,
    @Serializable(with = BigDecimalSerializer::class) val estimatedTimeInHour: BigDecimal? = BigDecimal.ONE,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedEndDate: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val estimatedStartDate: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
    private val workcenter: Workcenter? = null,
    private val operation: Operation? = null,
    private val item: Item? = null,
    private val order: Order? = null,
    private val users: List<User>? = emptyList(),
    private val technologies: List<Technology>? = emptyList(),
    private val worklogs: List<Worklog>? = emptyList()
)