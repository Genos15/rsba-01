package  com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class ItemStatistics(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    @Serializable(with = DateTimeSerializer::class) val addedInOrderAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
    val whoAdded: User? = null,
    val technologies: List<Technology>? = emptyList(),
    val totalNumberOfTasks: Int,
    val totalNumberOfTasksDone: Int
)
