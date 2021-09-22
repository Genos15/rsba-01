package  com.rsba.monitor_ms.domain.input

import  com.rsba.monitor_ms.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class RemoveEntityOfCustomerInput(
    @Serializable(with = UUIDSerializer::class) val customerId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val entityId: UUID? = null,
)
