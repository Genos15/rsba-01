package com.rsba.file_microservice.context.dataloader

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoaderRegistryFactory(
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
    }

    fun create(instanceId: UUID): DataLoaderRegistry {
        logger.warn { "+--- DataLoaderRegistryFactory -> create" }
        val registry = DataLoaderRegistry()
         logger.warn { "create = $instanceId" }
        return registry
    }


}

