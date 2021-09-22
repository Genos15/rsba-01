package com.rsba.usermicroservice.resolver.query

import com.rsba.usermicroservice.configuration.request_helper.CursorUtil
import com.rsba.usermicroservice.domain.input.ModuleWithPermission
import com.rsba.usermicroservice.domain.model.Role
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.RolesRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class RoleQueryResolver(
    val cursorUtil: CursorUtil,
    val service: RolesRepository,
    val logger: KLogger,
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveAllRoles(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Role>? {

        logger.warn { "+----- RoleQueryResolver/retrieveAllRoles" }

        val edges: List<Edge<Role>> =
            service.onRetrieveAll(first = first, after = after, environment = environment).map {
                return@map DefaultEdge(it, cursorUtil.createCursorWith(it.id))
            }.take(first)

        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )

        return DefaultConnection(edges, pageInfo)
    }

    @AdminSecured
    suspend fun retrieveModuleByRoleId(
        roleId: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<ModuleWithPermission>? {

        logger.warn { "+----- RoleQueryResolver/retrieveModuleByRoleId" }

        val edges: List<Edge<ModuleWithPermission>> =
            service.onRetrieveModuleById(roleId = roleId, first = first, after = after, environment = environment)
                .map {
                    return@map DefaultEdge(it, cursorUtil.createCursorWith(it.id))
                }.take(first)

        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )

        return DefaultConnection(edges, pageInfo)
    }
}