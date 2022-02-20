package com.rsba.usermicroservice.resolver.query

import com.rsba.usermicroservice.configuration.request_helper.CursorUtil
import com.rsba.usermicroservice.context.token.TokenImpl
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.domain.model.WorkingCenter
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.WorkingCenterRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import java.util.*


@Component
class WorkingCenterQueryResolver(
    private val logger: KLogger,
    val cursorUtil: CursorUtil,
    val service: WorkingCenterRepository,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver, GenericRetrieveConnection {

    @AdminSecured
    suspend fun retrieveAllWorkingCenters(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<WorkingCenter>? {
        logger.warn { "+WorkingCenterQueryResolver -> retrieveAllWorkingCenters" }
        val edges: List<Edge<WorkingCenter>> =
            service.retrieveAllWorkingCenters(
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


    suspend fun retrieveWorkcenterUsers(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<User> = perform(
        entries = service.retrieveUserOfWorkingCenter(
            ids = setOf(id),
            token = tokenImpl.read(environment = environment),
            first = first,
            after = after,
            userId = UUID.randomUUID()
        ),
        first = first,
        after = after,
        id = id
    )

    @AdminSecured
    suspend fun retrieveWorkingCenterById(id: UUID, environment: DataFetchingEnvironment): Optional<WorkingCenter> {
        logger.warn { "+WorkingCenterQueryResolver -> retrieveWorkingCenterById" }
        return service.retrieveWorkingCenterById(id = id, token = tokenImpl.read(environment = environment))
    }
}