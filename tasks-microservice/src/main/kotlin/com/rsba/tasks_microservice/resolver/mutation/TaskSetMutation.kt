package com.rsba.tasks_microservice.resolver.mutation

import  com.rsba.tasks_microservice.domain.input.*
import com.rsba.tasks_microservice.domain.model.MutationAction
import com.rsba.tasks_microservice.domain.model.TaskSet
import com.rsba.tasks_microservice.domain.repository.TaskSetRepository
import com.rsba.tasks_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskSetMutation(private val service: TaskSetRepository, private val deduct: TokenAnalyzer) :
    GraphQLMutationResolver {

    suspend fun createOrEditTaskSet(
        input: TaskSetInput,
        action: MutationAction? = null,
        environment: DataFetchingEnvironment
    ): Optional<TaskSet> =
        service.toCreateOrEdit(input = input, token = deduct(environment = environment), action = action)

    suspend fun executeTaskSet(
        id: UUID,
        quantity: Int? = null,
        environment: DataFetchingEnvironment
    ): Optional<TaskSet> =
        service.toExecute(id = id, token = deduct(environment = environment), quantity = quantity)

    suspend fun deleteTaskSet(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toDelete(input = input, token = deduct(environment = environment))

}