package  com.rsba.order_microservice.domain.input

import  com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateOrderInput(
    @Serializable(with = UUIDSerializer::class) val customerId: UUID? = null,
)
