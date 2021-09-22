package  com.rsba.order_microservice.domain.input

import  com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class RemoveEntityOfCustomerInput(
    @Serializable(with = UUIDSerializer::class) val customerId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val entityId: UUID? = null,
)
