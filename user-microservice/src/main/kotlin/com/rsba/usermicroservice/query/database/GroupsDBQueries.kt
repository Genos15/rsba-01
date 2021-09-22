package com.rsba.usermicroservice.query.database

import com.rsba.usermicroservice.domain.input.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object GroupsDBQueries {

    fun onCreateGroupQueryFrom(input: CreateGroupInput): String = "SELECT on_add_group('${input.toJson()}', '')"

    fun onAddUserInGroupQueryFrom(input: AddUserInGroupInput): String =
        "SELECT on_add_user_in_group('${input.toJson()}')"

    fun onDeleteUserInGroupQueryFrom(input: DeleteUserInGroupInput): String =
        "SELECT on_delete_user_in_group('${input.toJson()}')"

    fun onDeleteGroupsQueryFrom(input: UUID): String =
        "SELECT on_delete_group('$input')"

    fun onRetrieveAllGroupsQueryFrom(first: Int, after: UUID?): String =
        "SELECT on_retrieve_groups('$first', ${after?.let { "'$it'" }},'')"

    fun onAllocateRoleToGroupQueryFrom(input: AllocateRoleToGroupForDatabase): String =
        "SELECT on_edit_role_in_group('${input.toJson()}','')"

    fun retrieveGroupById(groupId: UUID, token: UUID): String =
        "SELECT on_retrieve_group_by_id('$groupId','$token')"

    fun retrieveWorkingCenterByGroupId(groupId: UUID, token: UUID): String =
        "SELECT on_retrieve_working_center_by_group_id('$groupId','$token')"

    fun retrieveManagersByGroupId(groupId: UUID, token: UUID): String =
        "SELECT on_retrieve_managers_by_group_id('$groupId','$token')"

    fun pickManager(input: ManagerInGroupInput, token: UUID): String =
        "SELECT on_pick_manager_in_group('${Json.encodeToString(input)}', '$token')"

    fun unpickManager(input: ManagerInGroupInput, token: UUID): String =
        "SELECT on_unpick_manager_in_group('${Json.encodeToString(input)}', '$token')"

}
