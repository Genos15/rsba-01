package  com.rsba.order_microservice.data.dao

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.DepartmentStatistics

@Serializable
@ModelType(_class = ModelTypeCase.departments_statistics)
data class DepartmentStatisticsDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val name: String,
    val description: String? = null,
    val priority: Int,
    val rowIndex: Int,
    val isActive: Boolean,
    val isDone: Boolean,
    val progress: Float,
    val workload: Float,
) : AbstractModel() {

    val to: DepartmentStatistics
        get() = DepartmentStatistics(
            id = id,
            name = name,
            description = description,
            priority = priority,
            rowIndex = rowIndex,
            isDone = isDone,
            isActive = isActive,
            progress = progress,
            workload = workload,
        )
}

