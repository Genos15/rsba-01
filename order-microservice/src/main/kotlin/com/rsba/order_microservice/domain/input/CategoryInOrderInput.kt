package com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CategoryInOrderInput(
    @Serializable(with = UUIDSerializer::class) val categoryId: UUID,
    val itemCount: Int? = null
)
