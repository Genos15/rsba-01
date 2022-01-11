package com.rsba.usermicroservice.domain.model

import java.util.*

data class CreateUserDatabaseParam(
//    val userId: UUID,
    val companyId: UUID? = null,
    val groupId: UUID? = null,
    val roleId: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    val middleName: String? = null,
    val login: String,
    val password: String,
    val phone: String? = null,
    val lang: String? = null
) : AbstractModel