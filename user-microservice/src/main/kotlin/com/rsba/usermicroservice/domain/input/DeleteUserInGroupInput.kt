package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class DeleteUserInGroupInput(
    val userId: UUID,
    val groupId: UUID
) : AbstractModel