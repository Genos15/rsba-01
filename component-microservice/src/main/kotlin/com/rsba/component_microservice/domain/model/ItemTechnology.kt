package com.rsba.component_microservice.domain.model

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ItemTechnology(
    @Serializable(with = UUIDSerializer::class) val itemId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val technologyId: UUID? = null
)
