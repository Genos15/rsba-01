package com.rsba.tasks_microservice.data.dao

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.domain.format.ModelType
import com.rsba.tasks_microservice.domain.format.ModelTypeCase
import com.rsba.tasks_microservice.domain.model.Technology
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.technologies)
data class TechnologyDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val name: String,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
) : AbstractModel() {

    val to: Technology
        get() = Technology(
            id = id,
            name = name,
            description = description,
            createdAt = createdAt,
            editedAt = editedAt,
        )
}
