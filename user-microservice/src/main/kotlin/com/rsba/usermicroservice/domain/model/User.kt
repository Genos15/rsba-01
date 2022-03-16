package com.rsba.usermicroservice.domain.model

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.SerialName
import java.util.*
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val login: String,
    val password: String? = null,
    val createdAt: String,
    val editedAt: String,
    @Serializable(with = UUIDSerializer::class) val createdBy: UUID? = null,
    val lang: String? = null,
    val deleted: Boolean? = null,
    val personalInfo: PersonalInfo? = null,
    val contactInfo: MutableList<ContactInfo>? = mutableListOf(),
    val role: Role? = null,
    val gender: String? = null,
    @SerialName(value = "pending") val pending: Boolean = true,
    @SerialName(value = "blocked") val blocked: Boolean = false,
    val departments: List<Group>? = emptyList(),
    @Serializable(with = UUIDSerializer::class) val photo: UUID? = null,
) : AbstractModel