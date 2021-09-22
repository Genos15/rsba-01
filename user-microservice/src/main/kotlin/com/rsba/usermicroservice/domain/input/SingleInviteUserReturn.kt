package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import com.rsba.usermicroservice.domain.model.AbstractModel
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class SingleInviteUserReturn(
    @Serializable(with = UUIDSerializer::class) val userId: UUID,
    @Serializable(with = UUIDSerializer::class) val companyId: UUID,
    @Serializable(with = UUIDSerializer::class) val groupId: UUID? = null,
    @Serializable(with = UUIDSerializer::class) val roleId: UUID,
    val companyName: String,
    val groupName: String? = null,
    val roleName: String,
    val email: String,
    val code: String,
    var lang: String? = null,
    var message: String? = null
) : AbstractModel