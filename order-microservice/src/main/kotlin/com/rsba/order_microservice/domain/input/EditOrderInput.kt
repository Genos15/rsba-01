package  com.rsba.order_microservice.domain.input

import  com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class EditOrderInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val agentId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val managerId: UUID? = null,
    val estimatedAt: String? = null,
    val description: String? = null
)