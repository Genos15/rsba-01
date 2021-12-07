package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class TechnologyInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val operations: List<TechnologyAndOperation>? = emptyList()
) : AbstractInput()
