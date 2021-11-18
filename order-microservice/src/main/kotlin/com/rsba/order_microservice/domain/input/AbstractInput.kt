package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@ModelType(_class = "abstract_input")
abstract class AbstractInput {
    @Serializable(with = UUIDSerializer::class)
    abstract val id: UUID?
}