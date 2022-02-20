package com.rsba.tasks_microservice.resolver.mutation

import  com.rsba.tasks_microservice.domain.input.*
import com.rsba.tasks_microservice.domain.model.MutationAction
import com.rsba.tasks_microservice.domain.model.Task
import com.rsba.tasks_microservice.domain.repository.TaskRepository
import com.rsba.tasks_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskMutation(private val service: TaskRepository, private val deduct: TokenAnalyzer) :
    GraphQLMutationResolver {

    suspend fun editTask(
        input: TaskInput,
        action: MutationAction? = null,
        environment: DataFetchingEnvironment
    ): Optional<Task> =
        service.toEdit(input = input, token = deduct(environment = environment), action = action)

    suspend fun executeTask(
        id: UUID,
        quantity: Int? = null,
        environment: DataFetchingEnvironment
    ): Optional<Task> =
        service.toExecute(id = id, token = deduct(environment = environment), quantity = quantity)

    suspend fun editWorkers(
        id: UUID,
        users: List<TaskWorkerTimeInput>,
        action: MutationAction? = null,
        environment: DataFetchingEnvironment
    ): Optional<Task> =
        service.toAllocate(id = id, users = users, token = deduct(environment = environment), action = action)

    suspend fun deleteTask(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toDelete(input = input, token = deduct(environment = environment))

    suspend fun executeTasksOfItem(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toExecuteTasksOfItem(input = input, token = deduct(environment = environment))

}