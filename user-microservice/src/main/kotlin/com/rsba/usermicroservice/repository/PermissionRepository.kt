package com.rsba.usermicroservice.repository

import com.rsba.usermicroservice.domain.input.ModuleWithPermission
import com.rsba.usermicroservice.domain.input.PermissionOfModule
import com.rsba.usermicroservice.domain.model.Permission
import java.util.*

interface PermissionRepository {
    suspend fun onRetrieveByModuleId(
        moduleIds: Set<UUID>,
        moduleId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Permission>>

    suspend fun onRetrieveByRoleId(
        roleIds: Set<UUID>,
        moduleId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Permission>>

    suspend fun onRetrievePermissionsOfModuleInRole(
        modulesInstances: Set<ModuleWithPermission>,
        moduleId: UUID,
        page: Int,
        size: Int
    ): Map<ModuleWithPermission, List<PermissionOfModule>>
}