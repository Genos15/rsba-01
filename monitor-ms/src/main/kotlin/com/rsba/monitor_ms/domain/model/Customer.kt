package  com.rsba.monitor_ms.domain.model

import  com.rsba.monitor_ms.deserializer.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Customer(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val representativeName: String? = null,
    val createdAt: String? = null,
    val editedAt: String? = null,
    @Serializable(with = UUIDSerializer::class) val creator: UUID? = null,
    val entities: MutableList<Customer>? = mutableListOf(),
    @SerialName("activeproductcount") val activeProductCount: Int? = 0
)

