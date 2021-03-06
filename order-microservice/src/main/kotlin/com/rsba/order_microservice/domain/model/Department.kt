package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.deserializer.DateTimeSerializer
import com.rsba.order_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class Department(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
//    val users: List<User>? = emptyList(),
    val managers: List<User>? = emptyList(),
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime? = null,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime? = null,
    val name: String,
    val description: String? = null,
    val priority: Int? = null,
    val isAnalytic: Boolean? = null,
    val isStaging: Boolean? = null,
    val workingCenters: List<WorkingCenter>? = emptyList(),
    @Serializable(with = UUIDSerializer::class)  val taskId: UUID
)
