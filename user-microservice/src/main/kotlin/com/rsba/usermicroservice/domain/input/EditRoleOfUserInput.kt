package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class EditRoleOfUserInput(
    val roleId: UUID,
    val userId: UUID
) : AbstractModel