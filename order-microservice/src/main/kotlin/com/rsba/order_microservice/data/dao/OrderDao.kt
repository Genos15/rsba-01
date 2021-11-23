package  com.rsba.order_microservice.data.dao

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.Order
import com.rsba.order_microservice.domain.model.OrderStatus
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.orders)
data class OrderDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val description: String? = null,
    val referenceNumber: String,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val startAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedAt: OffsetDateTime? = null,
    val progress: Float,
    @Serializable(with = DateTimeSerializer::class) val deliveryStartAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val deliveryEndAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val deliveryAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val packagingAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val receptionAt: OffsetDateTime? = null,
    val status: String
) : AbstractModel() {

    val to: Order
        get() = Order(
            id = id,
            description = description,
            referenceNumber = referenceNumber,
            createdAt = createdAt,
            editedAt = editedAt,
            startAt = startAt,
            estimatedAt = estimatedAt,
            progress = progress,
            deliveryStartAt = deliveryStartAt,
            deliveryEndAt = deliveryEndAt,
            deliveryAt = deliveryAt,
            packagingAt = packagingAt,
            receptionAt = receptionAt,
            status = OrderStatus.fromString(src = status)
        )

}

