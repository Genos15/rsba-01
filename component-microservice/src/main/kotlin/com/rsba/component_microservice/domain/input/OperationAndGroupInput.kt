package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class OperationAndGroupInput(
    @Serializable(with = UUIDSerializer::class) val operationId: UUID,
    @Serializable(with = UUIDSerializer::class) val groupId: UUID,
)