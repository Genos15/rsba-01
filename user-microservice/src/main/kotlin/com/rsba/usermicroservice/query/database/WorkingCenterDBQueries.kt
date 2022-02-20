package com.rsba.usermicroservice.query.database

import com.rsba.usermicroservice.domain.input.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object WorkingCenterDBQueries {

    fun createOrEdit(input: CreateOrEditWorkingCenterInput, token: UUID): String =
        "SELECT on_create_or_edit_workcenter('${Json.encodeToString(input)}', '$token')"

    fun delete(id: UUID, token: UUID): String =
        "SELECT on_delete_workcenter('$id', '$token')"

    fun retrieveAll(first: Int, after: UUID?, token: UUID): String =
        "SELECT on_retrieve_working_centers('$first', ${after?.let { "'$it'" }},'$token')"

    fun retrieveById(id: UUID, token: UUID): String =
        "SELECT on_retrieve_working_center_by_id('$id', '$token')"

    fun addUserInWorkingCenter(input: UserInWorkingCenterPayload, token: UUID): String =
        "SELECT on_add_user_in_workcenter('${Json.encodeToString(input)}', '$token')"

    fun deleteUserInWorkingCenter(input: UserInWorkingCenterPayload, token: UUID): String =
        "SELECT on_delete_user_in_workcenter('${Json.encodeToString(input)}', '$token')"

    fun pickManager(input: ManagerInWorkingCenterInput, token: UUID): String =
        "SELECT on_pick_manager_in_working_center('${Json.encodeToString(input)}', '$token')"

    fun unpickManager(input: ManagerInWorkingCenterInput, token: UUID): String =
        "SELECT on_unpick_manager_in_working_center('${Json.encodeToString(input)}', '$token')"

    fun retrieveUsersByWorkingCenterId(input: UUID, token: UUID): String =
        "SELECT on_retrieve_users_by_working_center_id('$input', '$token')"

    fun retrieveUsersByWorkcenterId(id: UUID, first: Int, after: UUID? = null, token: UUID): String =
        "select workcenters_on_retrieve_users('$id','$first', ${after?.let { "'$it'" }}, '$token')"

    fun retrieveManagersByWorkingCenterId(input: UUID, token: UUID): String =
        "SELECT on_retrieve_managers_by_working_center_id('$input', '$token')"
}
