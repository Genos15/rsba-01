package com.rsba.tasks_microservice.resolver.query

import com.rsba.tasks_microservice.domain.model.*
import com.rsba.tasks_microservice.domain.repository.TaskRepository
import com.rsba.tasks_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*

@Component
class TaskQueryResolver(val service: TaskRepository, private val deduct: TokenAnalyzer) :
    GraphQLQueryResolver, GenericRetrieveConnection {

    suspend fun retrieveTasks(
        first: Int,
        after: UUID? = null,
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
        environment: DataFetchingEnvironment
    ): Connection<Task> = perform(
        entries = service.retrieve(
            first = first,
            after = after,
            token = deduct(environment = environment),
            status = status,
            layer = layer,
            id = id,
            rangeStart = rangeStart,
            rangeEnd = rangeEnd,
        ),
        first = first,
        after = after
    )

    suspend fun searchTasks(
        input: String,
        first: Int,
        after: UUID? = null,
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
        environment: DataFetchingEnvironment
    ): Connection<Task> = perform(
        entries = service.search(
            input = input,
            first = first,
            after = after,
            token = deduct(environment = environment),
            status = status,
            layer = layer,
            id = id,
            rangeStart = rangeStart,
            rangeEnd = rangeEnd,
        ),
        first = first,
        after = after
    )

    suspend fun findTask(id: UUID, environment: DataFetchingEnvironment): Optional<Task> =
        service.find(id = id, token = deduct(environment = environment))

    suspend fun countTasks(
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        environment: DataFetchingEnvironment
    ): Int =
        service.count(token = deduct(environment = environment), status = status, layer = layer, id = id)

    suspend fun findTaskOrder(id: UUID, environment: DataFetchingEnvironment): Optional<Order> = perform(
        entries = service.order(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun findTaskItem(id: UUID, environment: DataFetchingEnvironment): Optional<Item> = perform(
        entries = service.item(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun findTaskOperation(id: UUID, environment: DataFetchingEnvironment): Optional<Operation> = perform(
        entries = service.operation(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun findTaskWorkcenter(id: UUID, environment: DataFetchingEnvironment): Optional<Workcenter> = perform(
        entries = service.workcenter(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun retrieveTaskUsers(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<User> = perform(
        entries = service.users(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun retrieveTaskComments(
        id: UUID,
        first: Int,
        after: UUID? = null,
        layer: CommentLayer? = null,
        environment: DataFetchingEnvironment
    ): Connection<Comment> = perform(
        entries = service.comments(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after,
            layer = layer
        ),
        first = first,
        after = after,
        id = id
    )


    suspend fun retrieveTaskTechnologies(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Technology> = perform(
        entries = service.technologies(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after,
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun retrieveTaskWorklogs(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Worklog> = perform(
        entries = service.worklogs(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after,
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun findUserWorkload(
        id: UUID,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        environment: DataFetchingEnvironment
    ): Float =
        service.userWorkload(
            id = id,
            token = deduct(environment = environment),
            rangeEnd = rangeEnd,
            rangeStart = rangeStart
        )

    suspend fun findWorkcenterWorkload(
        id: UUID,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        environment: DataFetchingEnvironment
    ): Float =
        service.workcenterWorkload(
            id = id,
            token = deduct(environment = environment),
            rangeEnd = rangeEnd,
            rangeStart = rangeStart
        )

    suspend fun retrieveUsersActivities(
        first: Int,
        after: UUID,
        layer: UserActivityLayer,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        environment: DataFetchingEnvironment
    ): Connection<User> = perform(
        entries = service.userActivities(
            first = first,
            token = deduct(environment = environment),
            after = after,
            layer = layer,
            rangeStart = rangeStart,
            rangeEnd = rangeEnd,
        ),
        first = first,
        after = after,
    )

}