package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase

@Serializable
@ModelType(_class = ModelTypeCase.orders_search_instances)
data class OrderSearchInputValue(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val input: List<String> = emptyList(),
    val first: Int,
    @Serializable(with = UUIDSerializer::class) val after: UUID? = null,
)