package com.rsba.usermicroservice.domain.model

import java.util.*

data class Module(
    val id: UUID,
    val name: String,
    val createdAt: String? = null,
    val editedAt: String? = null,
    val permissions: List<Permission>? = null
) : AbstractModel