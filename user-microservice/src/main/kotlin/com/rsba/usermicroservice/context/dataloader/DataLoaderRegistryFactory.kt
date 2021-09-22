package com.rsba.usermicroservice.context.dataloader

import com.rsba.usermicroservice.domain.model.Permission
import com.rsba.usermicroservice.repository.PermissionRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoaderRegistryFactory(
    val permissionRepository: PermissionRepository,
    private val forUser: UserDataLoaderImpl,
    private val forGroup: GroupDataLoaderImpl,
    private val forContactInfo: ContactInfoDataLoaderImpl,
    private val forPersonalInfo: PersonalInfoDataLoaderImpl,
    private val forRole: RoleDataLoaderImpl,
    private val forPermission: PermissionDataLoaderImpl,
    private val forWorkingCenter: WorkingCenterDataLoaderImpl,
    private val logger: KLogger
) {

    companion object {
        const val MODULE_DATA_LOADER = "MODULE_DATA_LOADER"
        const val ROLE_DATA_LOADER = "ROLE_DATA_LOADER"
        const val USER_DATA_LOADER = "USER_DATA_LOADER"
        const val CONTACT_INFO_DATA_LOADER = "CONTACT_INFO_DATA_LOADER"
        const val PERSONAL_INFO_DATA_LOADER = "PERSONAL_INFO_DATA_LOADER"
        const val ROLE_DATA_LOADER_GENERIC = "ROLE_DATA_LOADER_GENERIC"
        const val PERMISSION_DATA_LOADER_GENERIC = "PERMISSION_DATA_LOADER_GENERIC"
        const val USER_IN_WORKING_CENTER_DATA_LOADER = "USER_IN_WORKING_CENTER_DATA_LOADER"
        const val WORKING_CENTER_IN_GROUP_DATA_LOADER = "WORKING_CENTER_IN_GROUP_DATA_LOADER"
        const val MANAGERS_IN_GROUP_DATA_LOADER = "MANAGERS_IN_GROUP_DATA_LOADER"
        const val MANAGERS_IN_WORKING_CENTER_DATA_LOADER = "MANAGERS_IN_WORKING_CENTER_DATA_LOADER"
        const val GROUPS_OF_USERS_DATA_LOADER = "GROUPS_OF_USERS_DATA_LOADER"
    }

    fun create(instanceId: UUID): DataLoaderRegistry {
        logger.warn { "+--- DataLoaderRegistryFactory -> create" }
        val registry = DataLoaderRegistry()
        registry.register(MODULE_DATA_LOADER, dataLoaderFromModuleToPermissions(moduleId = instanceId))
        registry.register(ROLE_DATA_LOADER, dataLoaderFromRoleToPermissions(roleId = instanceId))
        registry.register(USER_DATA_LOADER, forUser.dataLoaderUserInGroup(groupId = instanceId))
        registry.register(CONTACT_INFO_DATA_LOADER, forContactInfo.dataLoaderContactsOfUser(userId = instanceId))
        registry.register(PERSONAL_INFO_DATA_LOADER, forPersonalInfo.dataLoaderPersonalInfoOfUser(userId = instanceId))
        registry.register(ROLE_DATA_LOADER_GENERIC, forRole.dataLoaderRoleOfUser(userId = instanceId))
        registry.register(
            PERMISSION_DATA_LOADER_GENERIC,
            forPermission.dataLoaderPermissionOfModuleInRole(userId = instanceId)
        )
        registry.register(
            USER_IN_WORKING_CENTER_DATA_LOADER,
            forWorkingCenter.dataLoaderUserInWorkingCenter(workingCenterId = instanceId)
        )
        registry.register(
            WORKING_CENTER_IN_GROUP_DATA_LOADER,
            forGroup.dataLoaderWorkingCenterInGroup(groupId = instanceId)
        )
        registry.register(MANAGERS_IN_GROUP_DATA_LOADER, forGroup.dataLoaderManagersInGroup(instanceId = instanceId))
        registry.register(
            MANAGERS_IN_WORKING_CENTER_DATA_LOADER,
            forWorkingCenter.dataLoaderManagersInWorkingCenter(instanceId = instanceId)
        )
        registry.register(GROUPS_OF_USERS_DATA_LOADER, forUser.dataLoaderGroupInUser(instanceId = instanceId))
        logger.warn { "create = $instanceId" }
        return registry
    }

    fun dataLoaderFromModuleToPermissions(moduleId: UUID): DataLoader<UUID, List<Permission>> {
        logger.warn { "+DataLoaderRegistryFactory/dataLoaderFromModuleToPermissions" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { "+env = {${env?.keyContextsList}}" }
            GlobalScope.future {
                permissionRepository.onRetrieveByModuleId(
                    moduleIds = ids,
                    moduleId = moduleId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }

    fun dataLoaderFromRoleToPermissions(roleId: UUID): DataLoader<UUID, List<Permission>> {
        logger.warn { "+DataLoaderRegistryFactory/dataLoaderFromRoleToPermissions" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { "+env = {${env?.keyContextsList}}" }
            GlobalScope.future {
                permissionRepository.onRetrieveByRoleId(
                    roleIds = ids,
                    moduleId = roleId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }


}

