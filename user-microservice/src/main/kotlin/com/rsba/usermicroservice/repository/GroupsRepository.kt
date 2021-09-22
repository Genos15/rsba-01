package com.rsba.usermicroservice.repository

import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.Group
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.domain.model.WorkingCenter
import java.util.*
import kotlin.jvm.Throws

interface GroupsRepository {
    @Throws(RuntimeException::class)
    suspend fun onCreate(input: CreateGroupInput, token: UUID): Optional<Group>

    @Throws(RuntimeException::class)
    suspend fun createOrEditGroup(input: CreateOrEditGroupInput, token: UUID): Optional<Group>

    @Throws(RuntimeException::class)
    suspend fun onAddUserInGroup(input: AddUserInGroupInput, token: UUID): Optional<Group>

    @Throws(RuntimeException::class)
    suspend fun onDeleteUserInGroup(input: DeleteUserInGroupInput, token: UUID): Int
    suspend fun onDeleteGroups(input: List<UUID>, token: UUID): Int
    suspend fun onRetrieveAll(first: Int, after: UUID?, token: UUID): MutableList<Group>
    suspend fun onAllocateRoleToGroup(input: AllocateRoleToGroup, token: UUID): Int

    @Throws(RuntimeException::class)
    suspend fun retrieveGroupById(groupId: UUID, token: UUID): Optional<Group>

    suspend fun retrieveWorkingCenterInGroup(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, List<WorkingCenter>>

    suspend fun retrieveManagersInGroup(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, List<User>>


    suspend fun addManagerInGroup(input: ManagerInGroupInput, token: UUID): Optional<Group>
    suspend fun deleteManagerInGroup(input: ManagerInGroupInput, token: UUID): Optional<Group>
}