package  com.rsba.order_microservice.domain.model

import  com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Order(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val description: String? = null,
    val referenceNumber: String,
    val createdAt: String? = null,
    val editedAt: String? = null,
    @Serializable(with = UUIDSerializer::class) val creator: UUID? = null,
    val customer: Customer? = null,
    val manager: Agent? = null,
    val agent: Agent? = null,
    val startAt: String? = null,
    val estimatedAt: String? = null,
    val progress: Float? = 0f,
    val status: String? = null,
    val items: List<Item>? = listOf(),
    val deleted: Boolean? = false,
    val isAnalyzing: Boolean? = true,
    val type: OrderType? = null
)
