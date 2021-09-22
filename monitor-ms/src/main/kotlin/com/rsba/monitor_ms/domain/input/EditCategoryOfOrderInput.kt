package  com.rsba.monitor_ms.domain.input

import  com.rsba.monitor_ms.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class EditCategoryOfOrderInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val categoryId: UUID? = null,
    val progress: Float? = 0f,
    val itemCount: Int? = 0
)