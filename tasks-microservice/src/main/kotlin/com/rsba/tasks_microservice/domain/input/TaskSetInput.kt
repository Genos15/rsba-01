package com.rsba.tasks_microservice.domain.input

import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class TaskSetInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID? = null,
    val label: String? = null,
    val users: List<String>? = emptyList(),
    val tasks: List<String>? = emptyList(),
) : AbstractInput()