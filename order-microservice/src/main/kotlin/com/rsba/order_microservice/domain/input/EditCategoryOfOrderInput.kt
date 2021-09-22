package  com.rsba.order_microservice.domain.input

import  com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class EditCategoryOfOrderInput(
    @Serializable(with = UUIDSerializer::class) val orderId: UUID,
    @Serializable(with = UUIDSerializer::class) val categoryId: UUID? = null,
    val progress: Float? = 0f,
    val itemCount: Int? = 0
)