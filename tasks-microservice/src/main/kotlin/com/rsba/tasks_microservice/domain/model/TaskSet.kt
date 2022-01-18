package com.rsba.tasks_microservice.domain.model

import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class TaskSet(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val label: String,
    val duration: Float,
    private val users: List<User>? = emptyList(),
    private val tasks: List<Task>? = emptyList()
)
