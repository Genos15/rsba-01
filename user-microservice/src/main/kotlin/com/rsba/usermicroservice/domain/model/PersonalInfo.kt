package com.rsba.usermicroservice.domain.model

import com.rsba.usermicroservice.configuration.convert.KlaxonProps.klaxon
import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PersonalInfo(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val firstname: String,
    val lastname: String,
    val middlename: String? = null,
    val birthday: String? = null,
    val createdAt: String? = null,
    val editedAt: String? = null,
) {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<PersonalInfo>(json)
    }
}
