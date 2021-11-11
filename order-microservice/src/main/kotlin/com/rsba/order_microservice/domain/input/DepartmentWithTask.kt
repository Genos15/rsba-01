package com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
data class DepartmentWithTask(
    @Serializable(with = UUIDSerializer::class) val taskId: UUID,
    val departmentIds: List<String>? = emptyList()
)