package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.OrderStatus
import java.time.OffsetDateTime

@Serializable
@ModelType(_class = ModelTypeCase.orders)
data class OrderInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val customerId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val typeId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val agentId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val managerId: UUID? = null,
    val referenceNumber: Int? = null,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val packagingAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val deliveryStartAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val deliveryEndAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val deliveryAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val receptionAt: OffsetDateTime? = null,
    val items: List<HostInput>? = emptyList(),
    val tasks: List<HostInput>? = emptyList(),
    val technologies: List<HostInput>? = emptyList(),
    val parameters: List<HostInput>? = emptyList(),
    val status: OrderStatus?
) : AbstractInput()