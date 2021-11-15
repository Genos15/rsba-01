package com.rsba.component_microservice.domain.model


import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class OperationEdition(
    @Serializable(with = UUIDSerializer::class) override val hostId: UUID,
    val case: OperationEditionCase
) : Edition()