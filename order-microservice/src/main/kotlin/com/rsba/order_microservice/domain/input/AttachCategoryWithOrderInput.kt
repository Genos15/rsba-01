package  com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AttachCategoryWithOrderInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    val categories: MutableList<CategoryInOrderInput> = mutableListOf()
)
