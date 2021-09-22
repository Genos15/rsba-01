package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class EditModuleInput(val id: UUID, val name: String? = null, val description: String? = null) : AbstractModel