package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class InviteUsersInput(
    val roleId: UUID,
    val groupId: UUID? = null,
    val message: String? = null,
    val email: MutableList<String>
) : AbstractModel
