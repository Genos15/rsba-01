package  com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class OrderStatistics(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val itemCategory: List<ItemCategoryStatistics>? = emptyList(),
    val departments: List<DepartmentStatistics>? = emptyList(),
)