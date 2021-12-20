package com.rsba.parameters_microservice.data.dao

import com.rsba.parameters_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.parameters_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.parameters_microservice.domain.format.ModelType
import com.rsba.parameters_microservice.domain.format.ModelTypeCase
import com.rsba.parameters_microservice.domain.model.Parameter
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.parameters)
data class ParameterDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val name: String,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val values: List<String> = emptyList()
) : AbstractModel() {

    val to: Parameter
        get() = Parameter(
            id = id,
            name = name,
            description = description,
            createdAt = createdAt,
            editedAt = editedAt,
            values = values
        )
}