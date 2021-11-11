package  com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer


@Serializable
data class CreateOrderInput(
    @Serializable(with = UUIDSerializer::class) val customerId: UUID,
    @Serializable(with = UUIDSerializer::class) val typeId: UUID? = null,
    val referenceNumber: Int? = null,
)
