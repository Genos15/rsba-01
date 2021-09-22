package  com.rsba.monitor_ms.domain.model

import  com.rsba.monitor_ms.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CategoryOfItemInOrder(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val itemCount: Int? = 0,
    val progress: Float? = 0f
)
