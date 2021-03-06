package com.rsba.usermicroservice.query.database

import com.rsba.usermicroservice.domain.input.CreateAdminInput
import com.rsba.usermicroservice.domain.input.LoginUserInput
import com.rsba.usermicroservice.domain.input.SingleInviteUsersInput
import com.rsba.usermicroservice.domain.model.CachedUserContact
import com.rsba.usermicroservice.domain.model.CreateUserDatabaseParam
import com.rsba.usermicroservice.domain.model.TokenHelper
import java.util.*

object UserDBQueries {

    fun onInsertQueryFrom(input: CreateUserDatabaseParam): String = "SELECT on_insert_user('${input.toJson()}', '')"

    fun onInsertAdminQueryFrom(input: CreateAdminInput): String = "SELECT oninsertuserandcompany('${input.toJson()}')"

    fun onConfirmEmailQueryFrom(input: CachedUserContact): String = "SELECT onconfirmemail('${input.toJson()}')"

    fun onBlockUsersQueryFrom(input: List<UUID>): String =
        "SELECT onblockuser('${input.joinToString(separator = ",") { it.toString() }}')"

    fun onLoginUserQueryFrom(input: LoginUserInput): String = "SELECT onloginuser('${input.toJson()}')"

    fun onLogoutUserQueryFrom(input: TokenHelper): String = "SELECT onlogoutuser('${input.toJson()}')"

    fun onInviteUserQueryFrom(input: SingleInviteUsersInput): String = "SELECT on_invite_user('${input.toJson()}', '')"

    fun onRetrieveByGroupId(groupId: UUID, first: Int, after: UUID? = null): String =
        "SELECT on_retrieve_users_by_group_id('$groupId','')"

    fun onRetrieveNotInGroup(first: Int, after: UUID? = null, token: UUID): String =
        "SELECT on_retrieve_users_not_in_group('$first', ${after?.let { "'$it'" }},'$token')"

    fun onRetrieveAll(first: Int, after: UUID? = null, token: UUID): String =
        "SELECT on_retrieve_all_users('$first', ${after?.let { "'$it'" }},'$token')"

    fun myGroups(userId: UUID): String =
        "SELECT on_retrieve_departments_by_user_id('$userId')"

    fun retrieveByToken(token: UUID): String =
        "SELECT on_retrieve_user_by_token('$token')"

}
