package com.rsba.tasks_microservice.domain.input

import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CommentInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val hostId: UUID? = null,
    val comment: String? = null,
) : AbstractInput()