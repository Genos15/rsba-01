package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel

data class CreateModuleInput(val name: String, val description: String? = null) : AbstractModel