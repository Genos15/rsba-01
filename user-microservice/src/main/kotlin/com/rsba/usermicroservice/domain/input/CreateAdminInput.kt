package com.rsba.usermicroservice.domain.input

 import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class CreateAdminInput(
    val email: String,
    val firstname: String,
    val lastname: String,
    val middlename: String,
    val companyName: String,
    val companyId: UUID?,
    val companyAddress: String?,
    val country: String,
    val phone: String,
    val login: String,
    val password: String
) : AbstractModel
