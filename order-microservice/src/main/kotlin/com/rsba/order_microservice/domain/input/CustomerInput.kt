package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CustomerInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val representativeName: String? = null,
    val entities: List<String>? = emptyList()
) : AbstractInput()
