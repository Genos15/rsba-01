package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.AbstractLayer
import com.rsba.order_microservice.domain.model.OrderLayerType
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.orders)
data class OrderLayer(
    val type: OrderLayerType,
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
) : AbstractInput(), AbstractLayer