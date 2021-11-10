package com.rsba.component_microservice.domain.model

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ElkGraphLink(
    var id: String,
    @Serializable(with = UUIDSerializer::class) val source: UUID,
    @Serializable(with = UUIDSerializer::class) val target: UUID,
)
