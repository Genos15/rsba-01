package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.context.token.TokenImpl
import com.rsba.order_microservice.domain.model.DraggableMap
import com.rsba.order_microservice.domain.model.Task
import com.rsba.order_microservice.domain.model.TaskLevel
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
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver, GenericRetrieveConnection(myLogger = logger) {

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
    ): Connection<Task>? = retrieveFn(
        entry = service.retrieveTasksByUserId(
            token = tokenImpl.read(environment = env),
            first = first,
            after = after,
            userId = userId
        ), first = first, after = after
    )

    @AdminSecured
    suspend fun retrieveTasksByUserToken(
        first: Int,
        after: UUID? = null,
        level: TaskLevel? = null,
        env: DataFetchingEnvironment
    ): Connection<Task>? = retrieveFn(
        entry = service.retrieveTasksByUserToken(
            token = tokenImpl.read(environment = env),
            first = first,
            after = after,
            level = level
        ), first = first, after = after
    )

    @AdminSecured
    suspend fun retrieveTasksByDepartmentId(
        departmentId: UUID,
        first: Int,
        after: UUID? = null,
        level: TaskLevel? = null,
        env: DataFetchingEnvironment
    ): Connection<Task>? = retrieveFn(
        entry = service.retrieveTasksByDepartmentId(
            token = tokenImpl.read(environment = env),
            first = first,
            after = after,
            level = level, departmentId = departmentId
        ), first = first, after = after
    )

    @AdminSecured
    suspend fun retrieveTasksByWorkingCenterId(
        workingCenterId: UUID,
        first: Int,
        after: UUID? = null,
        level: TaskLevel? = null,
        env: DataFetchingEnvironment
    ): Connection<Task>? = retrieveFn(
        entry = service.retrieveTasksByWorkingCenterId(
            token = tokenImpl.read(environment = env),
            first = first,
            after = after,
            level = level, workingCenterId = workingCenterId
        ), first = first, after = after
    )

    @AdminSecured
    suspend fun retrieveNumberOfTaskByUserId(userId: UUID, env: DataFetchingEnvironment): Optional<Int> {
        logger.warn { "+TaskQueryResolver -> retrieveNumberOfTaskByUserId" }
        return service.retrieveNumberOfTaskByUserId(userId = userId, token = tokenImpl.read(environment = env))
    }
}