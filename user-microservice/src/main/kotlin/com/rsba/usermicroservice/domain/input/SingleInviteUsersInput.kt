package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class SingleInviteUsersInput(
    val roleId: UUID,
    val groupId: UUID? = null,
    val email: String
) : AbstractModel