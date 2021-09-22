package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class WorkingCenterWithTask(
    @Serializable(with = UUIDSerializer::class) val taskId: UUID,
    val workingCenterIds: List<String>? = emptyList()
)