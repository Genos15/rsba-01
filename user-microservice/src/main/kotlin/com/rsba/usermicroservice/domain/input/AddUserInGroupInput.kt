package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class AddUserInGroupInput(
    val userId: UUID,
    val groupId: UUID,
    val occupationId: String? = null,
    val isManager: Boolean? = null,
) : AbstractModel