package com.rsba.order_microservice.data.dao

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.Item
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.items)
data class ItemDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val name: String,
    val description: String? = null,
    val material: String? = null,
    val quantity: Float? = null,
    val progress: Float? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val childrenIds: List<String>? = emptyList(),
) : AbstractModel() {

    val to: Item
        get() = Item(
            id = id,
            name = name,
            description = description,
            createdAt = createdAt,
            editedAt = editedAt,
            material = material,
            orderId = UUID.randomUUID(),
            progress = progress ?: 0f,
            quantity = quantity ?: 0f
        )
}
