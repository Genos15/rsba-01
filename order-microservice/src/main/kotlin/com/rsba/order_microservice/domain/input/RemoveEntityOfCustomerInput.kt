package  com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class RemoveEntityOfCustomerInput(
    @Serializable(with = UUIDSerializer::class) val customerId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val entityId: UUID? = null,
)
