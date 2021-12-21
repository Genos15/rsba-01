package com.rsba.tasks_microservice.resolver.query

import com.rsba.tasks_microservice.domain.model.Task
import com.rsba.tasks_microservice.domain.model.TaskLayer
import com.rsba.tasks_microservice.domain.model.TaskStatus
import com.rsba.tasks_microservice.domain.repository.TaskRepository
import com.rsba.tasks_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
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
        environment: DataFetchingEnvironment
    ): Connection<Task> = perform(
        entries = service.retrieve(
            first = first,
            after = after,
            token = deduct(environment = environment),
            status = status,
            layer = layer,
            id = id
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
        environment: DataFetchingEnvironment
    ): Connection<Task> = perform(
        entries = service.search(
            input = input,
            first = first,
            after = after,
            token = deduct(environment = environment),
            status = status,
            layer = layer,
            id = id
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
}