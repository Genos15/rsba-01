package  com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class AddEntityToCustomerInput(
    @Serializable(with = UUIDSerializer::class) val customerId: UUID? = null,
    val name: String,
    val description: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val representativeName: String? = null,
)
