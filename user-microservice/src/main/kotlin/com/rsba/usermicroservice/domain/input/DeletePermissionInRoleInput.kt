package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class DeletePermissionInRoleInput(
    val roleId: UUID,
    val permissionId: UUID,
) : AbstractModel