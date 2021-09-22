package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import java.util.*

data class AllocateRoleToGroup(
    val roleId: UUID,
    val groupId: UUID,
    val except: List<UUID>? = null,
) : AbstractModel


data class AllocateRoleToGroupForDatabase(private val income: AllocateRoleToGroup) : AbstractModel {
    val roleId: String = income.roleId.toString()
    val groupId: String = income.groupId.toString()
    val except: String = income.except?.joinToString(separator = ",") { it.toString() } ?: ""
}