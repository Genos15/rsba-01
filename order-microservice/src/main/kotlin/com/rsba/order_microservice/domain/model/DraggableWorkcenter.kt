package com.rsba.order_microservice.domain.model


import com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class DraggableWorkcenter(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val title: String,
    val tasksIds: List<String>,
)

