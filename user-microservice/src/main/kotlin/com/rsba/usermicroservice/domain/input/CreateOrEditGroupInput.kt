package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import com.rsba.usermicroservice.domain.model.AbstractModel
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateOrEditGroupInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    @Serializable(with = UUIDSerializer::class) val company: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val parent: UUID? = null,
    val priority: Int? = null,
    val isAnalytic: Boolean? = null,
    val isStaging: Boolean? = null,
) : AbstractModel