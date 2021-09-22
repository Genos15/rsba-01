package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class AddDependencyInModuleInput(
    val moduleId: UUID,
    val dependencyId: UUID,
) : AbstractModel