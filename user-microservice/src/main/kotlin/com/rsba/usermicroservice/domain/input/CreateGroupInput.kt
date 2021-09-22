package com.rsba.usermicroservice.domain.input

 import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class CreateGroupInput(
    val name: String,
    val description: String,
    val parentId: UUID? = null
) : AbstractModel