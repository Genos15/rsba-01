package com.rsba.usermicroservice.domain.model

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ContactInfo(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val value: String,
    val createdAt: String,
    val editedAt: String,
    val streetAddress: String? = null,
    val postalCode: String? = null,
    val city: String? = null,
    val stateProvince: String? = null
)
