package  com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ElkGraphInput(
    val height: Int,
    val width: Int,
    @Serializable(with = UUIDSerializer::class) val itemId: UUID,
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val parentId: UUID? = null
)