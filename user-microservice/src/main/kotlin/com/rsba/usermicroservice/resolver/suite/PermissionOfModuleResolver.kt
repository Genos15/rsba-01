package com.rsba.usermicroservice.resolver.suite

import com.rsba.usermicroservice.context.dataloader.DataLoaderRegistryFactory
import com.rsba.usermicroservice.domain.model.Module
import com.rsba.usermicroservice.domain.model.Permission
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class PermissionOfModuleResolver(
    private val logger: KLogger,
) : GraphQLResolver<Module> {


    fun permissions(module: Module, env: DataFetchingEnvironment): CompletableFuture<List<Permission>>? {
        logger.warn { "+------- PermissionOfModuleResolver/permissions" }
        val dataLoader = env.getDataLoader<UUID, List<Permission>>(DataLoaderRegistryFactory.MODULE_DATA_LOADER)
        return dataLoader?.load(module.id) ?: CompletableFuture.completedFuture(listOf())
    }

}