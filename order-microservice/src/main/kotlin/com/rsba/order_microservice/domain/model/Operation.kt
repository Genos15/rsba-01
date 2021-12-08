package  com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import java.time.OffsetDateTime

@Serializable
data class Operation(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val move: String? = null,
    val estimatedTimeInHour: Float? = 0f,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val departments: List<Group>? = emptyList(),
    val priority: Int? = null,
)
