package  com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.deserializer.DateTimeSerializer
import  com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class EditOrderInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val agentId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val managerId: UUID? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedAt: OffsetDateTime? = null,
    val description: String? = null,
    @Serializable(with = UUIDSerializer::class) val typeId: UUID? = null,
    @Serializable(with = DateTimeSerializer::class) val packagingAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val deliveryAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val receptionAt: OffsetDateTime? = null,
    val isSuspended: Boolean? = null
)