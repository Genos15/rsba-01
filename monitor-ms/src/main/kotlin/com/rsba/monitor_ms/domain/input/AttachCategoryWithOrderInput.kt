package  com.rsba.monitor_ms.domain.input

import  com.rsba.monitor_ms.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AttachCategoryWithOrderInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    val categories: MutableList<CategoryInOrderInput> = mutableListOf()
)
