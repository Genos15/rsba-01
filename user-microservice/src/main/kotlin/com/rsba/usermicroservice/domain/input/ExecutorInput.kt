package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class ExecutorInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val middlename: String? = null,
    val phone: String? = null,
    val email: String? = null,
    @Serializable(with = UUIDSerializer::class) val photo: UUID? = null
)
