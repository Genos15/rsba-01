package  com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.deserializer.DateTimeSerializer
import  com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class CategoryOfItem(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val items: MutableList<Item>? = mutableListOf(),
    @Serializable(with = UUIDSerializer::class) val orderId: UUID? = null,
    val color: String? = null,
    val progress: Float = 0f
)
