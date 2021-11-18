package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer

@Serializable
@ModelType(_class = ModelTypeCase.users)
data class TokenModel(
    @Serializable(with = UUIDSerializer::class) val token: UUID,
    @Serializable(with = UUIDSerializer::class) val refreshToken: UUID,
    @Serializable(with = DateTimeSerializer::class) val expireAt: OffsetDateTime? = null,
)