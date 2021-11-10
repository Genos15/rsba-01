package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ElkGraphLink(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    @Serializable(with = UUIDSerializer::class) val source: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val target: UUID,
)