package com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CommentTaskInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val taskId: UUID? = null,
    val comment: String? = null,
)
