package com.rsba.tasks_microservice.data.dao

import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.domain.format.ModelType
import com.rsba.tasks_microservice.domain.format.ModelTypeCase
import com.rsba.tasks_microservice.domain.model.Order
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.orders)
data class OrderDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val referenceNumber: String,
    val description: String? = null,
) : AbstractModel() {

    val to: Order
        get() = Order(
            id = id,
            referenceNumber = referenceNumber,
            description = description,
        )
}