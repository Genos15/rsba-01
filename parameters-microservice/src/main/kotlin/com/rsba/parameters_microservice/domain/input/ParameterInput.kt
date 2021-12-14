package com.rsba.parameters_microservice.domain.input

import com.rsba.parameters_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ParameterInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val value: String? = null,
    val hostIds: List<String>? = emptyList()
) : AbstractInput()