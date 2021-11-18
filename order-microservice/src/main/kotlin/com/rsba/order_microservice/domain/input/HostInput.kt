package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.orders)
data class HostInput(
    @Serializable(with = UUIDSerializer::class) val hostId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) override val id: UUID? = null,
) : AbstractInput()