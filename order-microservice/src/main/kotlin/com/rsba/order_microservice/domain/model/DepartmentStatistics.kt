package  com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class DepartmentStatistics(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val priority: Int,
    val rowIndex: Int,
    val isActive: Boolean,
    val isDone: Boolean,
    val progress: Float,
    val workload: Float,
)