package  com.rsba.monitor_ms.domain.input

import  com.rsba.monitor_ms.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateOrEditCustomerInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val representativeName: String? = null,
)