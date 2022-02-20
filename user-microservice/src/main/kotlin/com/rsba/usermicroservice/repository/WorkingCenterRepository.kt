package com.rsba.usermicroservice.repository

import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.domain.model.WorkingCenter
import java.util.*
import kotlin.jvm.Throws

interface WorkingCenterRepository {

    @Throws(RuntimeException::class)
    suspend fun createOrEdit(input: CreateOrEditWorkingCenterInput, token: UUID): Optional<WorkingCenter>
    suspend fun delete(input: UUID, token: UUID): Boolean
    suspend fun retrieveAllWorkingCenters(first: Int, after: UUID?, token: UUID): MutableList<WorkingCenter>
    suspend fun retrieveWorkingCenterById(id: UUID, token: UUID): Optional<WorkingCenter>
    suspend fun addUserInWorkingCenter(input: UserInWorkingCenterInput, token: UUID): Optional<WorkingCenter>
    suspend fun deleteUserInWorkingCenter(input: UserInWorkingCenterInput, token: UUID): Optional<WorkingCenter>
    suspend fun addManagerInWorkingCenter(input: ManagerInWorkingCenterInput, token: UUID): Optional<WorkingCenter>
    suspend fun deleteManagerInWorkingCenter(input: ManagerInWorkingCenterInput, token: UUID): Optional<WorkingCenter>
    suspend fun retrieveUserOfWorkingCenter(
        ids: Set<UUID>,
        userId: UUID,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<User>>

    suspend fun retrieveManagersOfWorkingCenter(
        ids: Set<UUID>,
        userId: UUID,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<User>>
}