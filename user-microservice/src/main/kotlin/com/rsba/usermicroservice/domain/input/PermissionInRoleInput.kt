package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class PermissionInRoleInput(
    val roleId: UUID,
    val permissionId: UUID,
    val canCreate: Boolean,
    val canEdit: Boolean,
    val canView: Boolean,
    val canDelete: Boolean,
) : AbstractModel
