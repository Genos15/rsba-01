package com.rsba.usermicroservice.resolver.suite

import com.rsba.usermicroservice.context.dataloader.DataLoaderRegistryFactory
import com.rsba.usermicroservice.domain.input.ModuleWithPermission
import com.rsba.usermicroservice.domain.input.PermissionOfModule
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class PermissionOfModuleInRoleResolver(
    private val logger: KLogger,
) : GraphQLResolver<ModuleWithPermission> {

    fun permissions(
        module: ModuleWithPermission,
        env: DataFetchingEnvironment
    ): CompletableFuture<MutableList<PermissionOfModule>>? {
        logger.warn { "+------- PermissionOfModuleInRoleResolver -> permissions" }
        val dataLoader =
            env.getDataLoader<ModuleWithPermission, MutableList<PermissionOfModule>>(DataLoaderRegistryFactory.PERMISSION_DATA_LOADER_GENERIC)
        return dataLoader?.load(module) ?: CompletableFuture.completedFuture(mutableListOf())
    }

}