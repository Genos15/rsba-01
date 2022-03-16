package  com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class OrderForSub(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val description: String? = null,
    val referenceNumber: String,
    val createdAt: String? = null,
    val editedAt: String? = null,
    @Serializable(with = UUIDSerializer::class) val createdBy: UUID? = null,
    var customer: Customer? = null,
    var manager: Agent? = null,
    var agent: Agent? = null,
    val startAt: String? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedAt: OffsetDateTime? = null,
    val progress: Float? = 0f,
    val status: String? = null,
    var categories: List<ItemCategory>? = listOf(),
    val deleted: Boolean? = false
) {

//    constructor(order: Order) : this(
//        id = order.id,
//        description = order.description,
//        referenceNumber = order.referenceNumber,
//        createdAt = order.createdAt,
//        editedAt = order.editedAt,
//        createdBy = order.createdBy,
//        customer = order.customer,
//        manager = order.manager,
//        agent = order.agent,
//        startAt = order.startAt,
//        estimatedAt = order.estimatedAt,
//        progress = order.progress,
//        status = order.status,
////        categories = order.categories,
//        categories = emptyList(),
//        deleted = order.deleted
//    )
}
