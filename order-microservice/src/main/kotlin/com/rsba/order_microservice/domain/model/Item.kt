package  com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.deserializer.DateTimeSerializer
import  com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class Item(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val material: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val operations: MutableList<Operation>? = mutableListOf(),
    val tasks: List<Task>? = emptyList(),
    val quantity: Float,
    val progress: Float,
    val category: CategoryOfItem? = null,
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
)
