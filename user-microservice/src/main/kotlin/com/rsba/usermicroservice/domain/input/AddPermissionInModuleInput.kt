package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class AddPermissionInModuleInput(
    val moduleId: UUID,
    val permissionName: String,
    val permissionDescription: String? = null,
) : AbstractModel