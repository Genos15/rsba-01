package com.rsba.component_microservice.domain.model

import com.example.ticketApp.deserializer.DateTimeSerializer
import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.component_microservice.domain.format.ModelType
import com.rsba.component_microservice.domain.format.ModelTypeCase
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.users)
data class TokenModel(
    @Serializable(with = UUIDSerializer::class) val token: UUID,
    @Serializable(with = UUIDSerializer::class) val refreshToken: UUID,
    @Serializable(with = DateTimeSerializer::class) val expireAt: OffsetDateTime? = null,
)