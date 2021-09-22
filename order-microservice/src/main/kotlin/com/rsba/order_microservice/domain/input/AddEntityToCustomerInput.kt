package  com.rsba.order_microservice.domain.input

import  com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AddEntityToCustomerInput(
    @Serializable(with = UUIDSerializer::class) val customerId: UUID? = null,
    val name: String,
    val description: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val representativeName: String? = null,
)
