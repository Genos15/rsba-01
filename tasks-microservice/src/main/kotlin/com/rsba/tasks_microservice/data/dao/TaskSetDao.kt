package com.rsba.tasks_microservice.data.dao

import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.domain.format.ModelType
import com.rsba.tasks_microservice.domain.format.ModelTypeCase
import com.rsba.tasks_microservice.domain.model.TaskSet
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.tasks_set)
data class TaskSetDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val label: String,
    val duration: Float,
) : AbstractModel() {

    val to: TaskSet
        get() = TaskSet(id = id, duration = duration, label = label)
}