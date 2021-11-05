package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ItemTechnologyInput(
    @Serializable(with = UUIDSerializer::class) val itemId: UUID? = null,
    val technologiesIds: List<String>? = emptyList()
)
