package  com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Operation(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val move: String? = null,
    val estimatedTimeInHour: Float? = 0f,
    val createdAt: String? = null,
    val editedAt: String? = null,
    val departments: List<Group>? = listOf()
)
