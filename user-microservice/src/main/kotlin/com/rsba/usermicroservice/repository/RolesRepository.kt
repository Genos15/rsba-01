package com.rsba.usermicroservice.repository

import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.Role
import graphql.schema.DataFetchingEnvironment
import java.util.*
import kotlin.jvm.Throws

interface RolesRepository {
    @Throws(RuntimeException::class)
    suspend fun onCreate(input: CreateRoleInput, environment: DataFetchingEnvironment): Optional<Role>

    @Throws(RuntimeException::class)
    suspend fun onEditRoleOfUser(input: EditRoleOfUserInput, environment: DataFetchingEnvironment): Optional<Role>

    @Throws(RuntimeException::class)
    suspend fun onAddPermissionInRole(
        input: PermissionInRoleInput,
        environment: DataFetchingEnvironment
    ): Optional<Role>

    @Throws(RuntimeException::class)
    suspend fun onEditPermissionInRole(
        input: PermissionInRoleInput,
        environment: DataFetchingEnvironment
    ): Optional<Role>

    @Throws(RuntimeException::class)
    suspend fun onDeletePermissionInRole(input: DeletePermissionInRoleInput, environment: DataFetchingEnvironment): Int

    suspend fun onDeleteRoles(input: List<UUID>, environment: DataFetchingEnvironment): Int

    suspend fun onRetrieveAll(first: Int, after: UUID? = null, environment: DataFetchingEnvironment): MutableList<Role>

    suspend fun onRetrieveByUserId(
        userIds: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, Role?>

    suspend fun onRetrieveModuleById(
        roleId: UUID,
        first: Int,
        after: UUID? = null, environment: DataFetchingEnvironment
    ): MutableList<ModuleWithPermission>

}