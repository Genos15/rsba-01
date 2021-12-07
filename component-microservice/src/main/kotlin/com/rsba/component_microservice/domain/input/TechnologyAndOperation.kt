package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class TechnologyAndOperation(
    @Serializable(with = UUIDSerializer::class) val operationId: UUID? = null,
    val priority: Int? = null
)
