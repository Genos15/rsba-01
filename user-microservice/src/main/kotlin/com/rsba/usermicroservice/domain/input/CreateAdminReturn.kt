package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel

data class CreateAdminReturn(
    val login: String,
    val companyname: String,
    val email: String
) : AbstractModel