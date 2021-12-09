package  com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class ItemCategoryStatistics(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val numberOfItems: Int,
    val progress: Float = 0f,
    val name: String
)