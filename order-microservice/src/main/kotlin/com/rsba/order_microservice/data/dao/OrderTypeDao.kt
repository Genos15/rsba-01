package  com.rsba.order_microservice.data.dao

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.OrderType

@Serializable

@ModelType(_class = ModelTypeCase.order_types)
data class OrderTypeDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val name: String,
    val description: String? = null,
    val isDefault: Boolean
) : AbstractModel() {

    val to: OrderType
        get() = OrderType(
            id = id,
            description = description,
            name = name,
            isDefault = isDefault,
        )

}
