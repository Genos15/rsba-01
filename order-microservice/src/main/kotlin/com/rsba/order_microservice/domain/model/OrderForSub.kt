package  com.rsba.order_microservice.domain.model

import  com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class OrderForSub(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val description: String? = null,
    val referenceNumber: String,
    val createdAt: String? = null,
    val editedAt: String? = null,
    @Serializable(with = UUIDSerializer::class) val creator: UUID? = null,
    var customer: Customer? = null,
    var manager: Agent? = null,
    var agent: Agent? = null,
    val startAt: String? = null,
    val estimatedAt: String? = null,
    val progress: Float? = 0f,
    val status: String? = null,
    var categories: List<CategoryOfItem>? = listOf(),
    val deleted: Boolean? = false
) {

    constructor(order: Order) : this(
        id = order.id,
        description = order.description,
        referenceNumber = order.referenceNumber,
        createdAt = order.createdAt,
        editedAt = order.editedAt,
        creator = order.creator,
        customer = order.customer,
        manager = order.manager,
        agent = order.agent,
        startAt = order.startAt,
        estimatedAt = order.estimatedAt,
        progress = order.progress,
        status = order.status,
//        categories = order.categories,
        categories = emptyList(),
        deleted = order.deleted
    )
}
