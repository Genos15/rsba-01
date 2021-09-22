package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.configuration.request_helper.CursorUtil
import com.rsba.order_microservice.context.token.TokenImpl
import com.rsba.order_microservice.domain.model.DraggableMap
import com.rsba.order_microservice.domain.model.Task
import com.rsba.order_microservice.repository.TaskRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import java.util.*


@Component
class TaskQueryResolver(
    private val service: TaskRepository,
    private val logger: KLogger,
    private val cursorUtil: CursorUtil,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveTasksByGroupId(id: UUID, env: DataFetchingEnvironment): Optional<DraggableMap> {
        logger.warn { "+TaskQueryResolver -> retrieveByGroupId" }
        return service.retrieveTasksByGroupId(input = id, token = tokenImpl.read(environment = env))
    }

    @AdminSecured
    suspend fun retrieveTaskById(id: UUID, env: DataFetchingEnvironment): Optional<Task> {
        logger.warn { "+TaskQueryResolver -> retrieveTaskById" }
        return service.retrieveTasksById(input = id, token = tokenImpl.read(environment = env))
    }

    @AdminSecured
    suspend fun retrieveTasksByUserId(
        userId: UUID,
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Task>? {
        logger.warn { "+TaskQueryResolver -> retrieveTasksByUserId" }
        val edges: List<Edge<Task>> =
            service.retrieveTasksByUserId(
                token = tokenImpl.read(environment = env),
                first = first,
                after = after,
                userId = userId
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
    suspend fun retrieveTasksByUserToken(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Task>? {
        logger.warn { "+TaskQueryResolver -> retrieveTasksByUserToken" }
        val edges: List<Edge<Task>> =
            service.retrieveTasksByUserToken(
                token = tokenImpl.read(environment = env),
                first = first,
                after = after
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
    suspend fun retrieveNumberOfTaskByUserId(userId: UUID, env: DataFetchingEnvironment): Optional<Int> {
        logger.warn { "+TaskQueryResolver -> retrieveNumberOfTaskByUserId" }
        return service.retrieveNumberOfTaskByUserId(userId = userId, token = tokenImpl.read(environment = env))
    }
}