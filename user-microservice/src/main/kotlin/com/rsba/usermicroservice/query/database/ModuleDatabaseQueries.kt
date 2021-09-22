package com.rsba.usermicroservice.query.database

import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.User
import java.util.*

object ModuleDatabaseQueries {

    fun onCreateModuleQueryFrom(input: CreateModuleInput): String = "SELECT on_create_module('${input.toJson()}', '')"

    fun onEditModuleQueryFrom(input: EditModuleInput): String = "SELECT on_edit_module('${input.toJson()}', '')"

    fun onAddDependencyInModuleQueryFrom(input: AddDependencyInModuleInput): String =
        "SELECT on_add_dependency_in_module('${input.toJson()}', '')"

    fun onAddPermissionInModuleQueryFrom(input: AddPermissionInModuleInput): String =
        "SELECT on_add_permission_in_module('${input.toJson()}', '')"

    fun onDeletePermissionInModuleQueryFrom(input: DeletePermissionInModuleInput): String =
        "SELECT on_delete_permission_in_module('${input.toJson()}', '')"

    fun onRetrieveModuleQueryFrom(user: User? = null, first: Int = 10, after: UUID? = null): String =
        "SELECT on_retrieve_modules('$first',  ${after?.let { "'$it'" }}, '')"

}
