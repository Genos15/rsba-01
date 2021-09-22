package com.rsba.usermicroservice.context.dataloader

import com.rsba.usermicroservice.domain.input.ModuleWithPermission
import com.rsba.usermicroservice.domain.input.PermissionOfModule
import com.rsba.usermicroservice.repository.PermissionRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class PermissionDataLoaderImpl(private val logger: KLogger, private val service: PermissionRepository) {

    fun dataLoaderPermissionOfModuleInRole(userId: UUID): DataLoader<ModuleWithPermission, List<PermissionOfModule>> {
        logger.warn { "+--- PermissionDataLoaderImpl -> dataLoaderPermissionOfModuleInRole" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.onRetrievePermissionsOfModuleInRole(
                    modulesInstances = ids,
                    moduleId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }
}