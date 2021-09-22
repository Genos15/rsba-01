package com.rsba.usermicroservice.resolver.suite

import com.rsba.usermicroservice.context.dataloader.DataLoaderRegistryFactory
import com.rsba.usermicroservice.domain.model.Permission
import com.rsba.usermicroservice.domain.model.Role
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class PermissionOfRoleResolver(
    private val logger: KLogger,
) : GraphQLResolver<Role> {

    fun permissions(role: Role, env: DataFetchingEnvironment): CompletableFuture<List<Permission>>? {
        logger.warn { "+------- PermissionOfRoleResolver/permissions" }
        val dataLoader = env.getDataLoader<UUID, List<Permission>>(DataLoaderRegistryFactory.ROLE_DATA_LOADER)
        return dataLoader?.load(role.id) ?: CompletableFuture.completedFuture(listOf())
    }

}