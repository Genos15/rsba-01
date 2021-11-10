package com.rsba.component_microservice.domain.model

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
abstract class ElkGraphNode(
    @Serializable(with = UUIDSerializer::class) open val id: UUID,
    open val height: Int,
    open val width: Int
)
