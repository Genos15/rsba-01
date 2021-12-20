package com.rsba.parameters_microservice.data.dao

import com.rsba.parameters_microservice.configuration.deserializer.AbstractSerializer
import com.rsba.parameters_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.parameters_microservice.domain.format.ModelType
import kotlinx.serialization.Serializable
import java.util.*

@Serializable(with = AbstractSerializer::class)
@ModelType(_class = "abstract")
sealed class AbstractModel {
    @Serializable(with = UUIDSerializer::class)
    abstract val id: UUID
}