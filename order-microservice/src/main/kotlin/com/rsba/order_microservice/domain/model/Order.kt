package  com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase

@Serializable
@ModelType(_class = ModelTypeCase.orders)
data class Order(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val description: String? = null,
    val referenceNumber: String,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
    val customer: Customer? = null,
    val manager: Agent? = null,
    val agent: Agent? = null,
    @Serializable(with = DateTimeSerializer::class) val startAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedAt: OffsetDateTime? = null,
    val progress: Float,
    val type: OrderType? = null,
    @Serializable(with = DateTimeSerializer::class) val deliveryStartAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val deliveryEndAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val deliveryAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val packagingAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val receptionAt: OffsetDateTime? = null,
    val items: List<Item>? = emptyList(),
    val tasks: List<Task>? = emptyList(),
    val technologies: List<Technology>? = emptyList(),
    val parameters: List<Parameter>? = emptyList(),
    val categories: List<ItemCategory>? = emptyList(),
    val worklogs: List<Worklog>? = emptyList(),
    val status: OrderStatus,
    val statistics: OrderStatistics? = null,
)
