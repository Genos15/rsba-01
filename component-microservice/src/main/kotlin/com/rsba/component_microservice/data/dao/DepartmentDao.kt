package com.rsba.component_microservice.data.dao

import com.example.ticketApp.deserializer.DateTimeSerializer
import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.component_microservice.domain.format.ModelType
import com.rsba.component_microservice.domain.format.ModelTypeCase
import com.rsba.component_microservice.domain.model.Group

import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.departments)
data class DepartmentDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val name: String,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val priority: Int? = 0,
    val isAnalytic: Boolean? = null,
    val isStaging: Boolean? = null,
) : AbstractModel() {

    val to: Group
        get() = Group(
            id = id,
            name = name,
            description = description,
            createdAt = createdAt,
            editedAt = editedAt,
            priority = priority,
            isAnalytic = isAnalytic,
            isStaging = isStaging,
        )

}