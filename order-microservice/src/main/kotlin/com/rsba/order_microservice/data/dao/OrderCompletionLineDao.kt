package com.rsba.order_microservice.data.dao

import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.OrderCompletionLine
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.orders_completion_line)
data class OrderCompletionLineDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val created: Map<String, Int>,
    val delivered: Map<String, Int>,
    val done: Map<String, Int>,
) : AbstractModel() {

    val to: OrderCompletionLine
        get() = OrderCompletionLine(
            created = created,
            delivered = delivered,
            done = done,
        )
}
