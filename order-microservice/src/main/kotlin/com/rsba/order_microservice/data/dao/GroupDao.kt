package com.rsba.order_microservice.data.dao

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.Group
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.departments)
data class GroupDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val name: String,
    val description: String? = null,
    val priority: Int? = 0,
    val isAnalytic: Boolean? = null,
    val isStaging: Boolean? = null,
) : AbstractModel() {

    val to: Group
        get() = Group(
            id = id,
            description = description,
            createdAt = createdAt,
            editedAt = editedAt,
            name = name,
            priority = priority,
            isAnalytic = isAnalytic,
            isStaging = isStaging
        )
}
