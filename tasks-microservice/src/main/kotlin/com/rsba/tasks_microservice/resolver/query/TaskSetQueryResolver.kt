package com.rsba.tasks_microservice.resolver.query

import com.rsba.tasks_microservice.domain.model.*
import com.rsba.tasks_microservice.domain.repository.TaskSetRepository
import com.rsba.tasks_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*

@Component
class TaskSetQueryResolver(val service: TaskSetRepository, private val deduct: TokenAnalyzer) :
    GraphQLQueryResolver, GenericRetrieveConnection {

    suspend fun retrieveTaskSets(
        first: Int,
        id: UUID? = null,
        after: UUID? = null,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
        environment: DataFetchingEnvironment
    ): Connection<TaskSet> = perform(
        entries = service.retrieve(
            first = first,
            after = after,
            token = deduct(environment = environment),
            rangeStart = rangeStart,
            rangeEnd = rangeEnd,
            id = id
        ),
        first = first,
        after = after
    )


    suspend fun findTaskSet(id: UUID, environment: DataFetchingEnvironment): Optional<TaskSet> =
        service.find(id = id, token = deduct(environment = environment))

    suspend fun countTaskSets(environment: DataFetchingEnvironment): Int =
        service.count(token = deduct(environment = environment))

    suspend fun retrieveTaskSetUsers(
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

    suspend fun retrieveTaskSetComments(
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

    suspend fun retrieveTaskSet_Tasks(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Task> = perform(
        entries = service.tasks(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

}