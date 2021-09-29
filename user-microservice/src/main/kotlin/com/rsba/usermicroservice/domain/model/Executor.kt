package com.rsba.usermicroservice.domain.model

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Executor(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val firstname: String,
    val lastname: String,
    val middlename: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val pin: String? = null,
    @Serializable(with = UUIDSerializer::class) var photo: UUID? = null
)