package  com.rsba.order_microservice.data.dao

import com.example.ticketApp.deserializer.DateTimeSerializer
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.orders)
data class OrderDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val description: String? = null,
    val referenceNumber: String,
    val createdAt: String? = null,
    val editedAt: String? = null,
    @Serializable(with = UUIDSerializer::class) val creator: UUID? = null,
    val startAt: String? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedAt: OffsetDateTime? = null,
    val progress: Float? = 0f,
    val status: String? = null,
    val deleted: Boolean? = false,
    val isAnalyzing: Boolean? = true,
    @Serializable(with = DateTimeSerializer::class) val packagingAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val deliveryAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val receptionAt: OffsetDateTime? = null,
    val isSuspended: Boolean
) : AbstractModel()
