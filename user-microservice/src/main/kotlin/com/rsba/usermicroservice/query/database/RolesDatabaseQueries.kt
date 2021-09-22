package com.rsba.usermicroservice.query.database

import com.rsba.usermicroservice.domain.input.*
import java.util.*

object RolesDatabaseQueries {

    fun onCreateRoleQueryFrom(input: CreateRoleInput, token: UUID): String =
        "SELECT on_add_role_without_permission('${input.name}', '$token')"

    fun onEditRoleOfUserQueryFrom(input: EditRoleOfUserInput, token: UUID): String =
        "SELECT on_edit_role_in_user('${input.toJson()}', '$token')"

    fun onAddPermissionInRoleQueryFrom(input: PermissionInRoleInput, token: UUID): String =
        "SELECT on_add_permission_in_role('${input.toJson()}', '$token')"

    fun onEditPermissionInRoleQueryFrom(input: PermissionInRoleInput): String =
        "SELECT on_edit_permission_in_role('${input.toJson()}')"

    fun onDeletePermissionInRoleQueryFrom(input: DeletePermissionInRoleInput): String =
        "SELECT on_delete_permission_in_role('${input.toJson()}')"

    fun onDeleteRolesQueryFrom(input: List<UUID>): String =
        "SELECT on_delete_role('${input.joinToString(separator = ",") { it.toString() }}')"

    fun onRetrieveAllRolesQueryFrom(token: UUID, first: Int, after: UUID?): String = "SELECT on_retrieve_roles('$first', ${after?.let { "'$it'" }}, '$token')"

    fun onRetrieveByUserId(userId: UUID): String =
        "SELECT on_retrieve_role_by_user_id('$userId','')"

    fun onRetrieveModuleByRoleId(roleId: UUID, token: UUID, first: Int, after: UUID?): String =
        "SELECT on_retrieve_module_by_role_id('$roleId','$token')"

}
