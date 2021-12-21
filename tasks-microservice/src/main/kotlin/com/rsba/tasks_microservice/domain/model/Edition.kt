package com.rsba.tasks_microservice.domain.model


import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
sealed class Edition {
    @Serializable(with = UUIDSerializer::class)
    abstract val hostId: UUID
}