package  com.rsba.order_microservice.data.dao

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.ItemStatistics

@Serializable
@ModelType(_class = ModelTypeCase.items_statistics)
data class ItemStatisticsDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    @Serializable(with = DateTimeSerializer::class) val addedInOrderAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
    val totalNumberOfTasks: Int,
    val totalNumberOfTasksDone: Int
) : AbstractModel() {

    val to: ItemStatistics
        get() = ItemStatistics(
            id = id,
            createdAt = createdAt,
            editedAt = editedAt,
            addedInOrderAt = addedInOrderAt,
            totalNumberOfTasksDone = totalNumberOfTasksDone,
            totalNumberOfTasks = totalNumberOfTasks
        )
}

