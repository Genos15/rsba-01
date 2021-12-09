package  com.rsba.order_microservice.data.dao

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.ItemCategoryStatistics

@Serializable
@ModelType(_class = ModelTypeCase.item_category_statistics)
data class ItemCategoryStatisticsDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val numberOfItems: Int,
    val progress: Float = 0f,
    val name: String
) : AbstractModel() {

    val to: ItemCategoryStatistics
        get() = ItemCategoryStatistics(
            id = id,
            numberOfItems = numberOfItems,
            progress = progress,
            name = name,
        )
}

