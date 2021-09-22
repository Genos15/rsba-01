package com.rsba.usermicroservice.resolver.query

import com.rsba.usermicroservice.configuration.request_helper.CursorUtil
import com.rsba.usermicroservice.context.token.TokenImpl
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.NotificationRepository
import com.rsba.usermicroservice.service.UserService
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import java.util.*


@Component
class UserQueryResolver(
    val cursorUtil: CursorUtil,
    val service: UserService,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl,
    private val notifier: NotificationRepository
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveAllUsers(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<User>? {
        val edges: List<Edge<User>> =
            service.onRetrieveAll(first = first, after = after, token = tokenImpl.read(environment = environment))
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

    @AdminSecured
    suspend fun retrieveAllNotInGroup(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<User>? {
        val edges: List<Edge<User>> =
            service.onRetrieveNotInGroup(
                first = first,
                after = after,
                token = tokenImpl.read(environment = environment)
            ).map {
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
    suspend fun retrieveUserByToken(environment: DataFetchingEnvironment): Optional<User> =
        service.retrieveByToken(token = tokenImpl.read(environment = environment))

}