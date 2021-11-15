package com.rsba.component_microservice.domain.model


import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Edition<out T : EditionCase>(@Serializable(with = UUIDSerializer::class) val hostId: UUID, val case: T)