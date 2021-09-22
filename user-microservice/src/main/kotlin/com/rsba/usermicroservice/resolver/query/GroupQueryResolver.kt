package com.rsba.usermicroservice.resolver.query

import com.rsba.usermicroservice.configuration.request_helper.CursorUtil
import com.rsba.usermicroservice.context.token.TokenImpl
import com.rsba.usermicroservice.domain.model.Group
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.GroupsRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import java.util.*


@Component
class GroupQueryResolver(
    private val logger: KLogger,
    val cursorUtil: CursorUtil,
    val service: GroupsRepository,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveAllGroups(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Group>? {
        val edges: List<Edge<Group>> =
            service.onRetrieveAll(first = first, after = after, token = tokenImpl.read(environment = environment)).map {
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
    suspend fun retrieveGroupById(groupId: UUID, environment: DataFetchingEnvironment): Optional<Group> {
        logger.warn { "+GroupQueryResolver -> retrieveGroupById" }
        return service.retrieveGroupById(groupId = groupId, token = tokenImpl.read(environment = environment))
    }
}