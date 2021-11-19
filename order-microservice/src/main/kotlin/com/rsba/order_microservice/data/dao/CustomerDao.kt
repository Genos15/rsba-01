package  com.rsba.order_microservice.data.dao

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.Customer
import java.time.OffsetDateTime

@Serializable

@ModelType(_class = ModelTypeCase.customers)
data class CustomerDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val name: String,
    val description: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val representativeName: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
    val activeProductCount: Int? = 0
) : AbstractModel() {

    val to: Customer
        get() = Customer(
            id = id,
            description = description,
            email = email,
            phone = phone,
            name = name,
            createdAt = createdAt,
            editedAt = editedAt,
            activeProductCount = activeProductCount,
            representativeName = representativeName
        )

}
