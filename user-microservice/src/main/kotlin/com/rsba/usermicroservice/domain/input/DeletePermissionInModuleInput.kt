package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class DeletePermissionInModuleInput(
    val moduleId: UUID,
    val permissionId: UUID,
) : AbstractModel