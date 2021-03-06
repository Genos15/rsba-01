package com.rsba.usermicroservice.repository

import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.Group
import com.rsba.usermicroservice.domain.model.User
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono
import java.util.*
import kotlin.jvm.Throws

interface UserRepository : AbstractCRUDRepository<User, CreateUserInput, CreateAdminInput> {
    fun onConfirmEmail(email: String, code: String): Mono<String>
    suspend fun onInviteUsers(
        input: InviteUsersInput,
        environment: DataFetchingEnvironment
    ): List<SingleInviteUserReturn>?

    suspend fun onBlockUsers(input: List<UUID>, environment: DataFetchingEnvironment): Int = 0

    @Throws(RuntimeException::class)
    suspend fun onLoginUser(input: LoginUserInput): Optional<LoginUserReturn> {
        return Optional.empty()
    }

    suspend fun onLogOut(environment: DataFetchingEnvironment): Optional<Int> {
        return Optional.empty()
    }

    suspend fun onRetrieveNotInGroup(first: Int = 10, after: UUID?, token: UUID): List<User>

    suspend fun onRetrieveUserInGroup(
        groupIds: Set<UUID>,
        groupId: UUID,
        first: Int,
        after: UUID?
    ): Map<UUID, List<User>>

    suspend fun myGroups(
        ids: Set<UUID>,
        instanceId: UUID,
        first: Int,
        after: UUID?
    ): Map<UUID, List<Group>>

    suspend fun retrieveByToken(token: UUID): Optional<User>
}