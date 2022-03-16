package com.rsba.tasks_microservice.data.service.implementations

import com.rsba.tasks_microservice.domain.input.TaskSetInput
import com.rsba.tasks_microservice.domain.model.*
import com.rsba.tasks_microservice.domain.repository.TaskSetRepository
import com.rsba.tasks_microservice.domain.usecase.common.*
import com.rsba.tasks_microservice.domain.usecase.common.custom.task_set.CreateOrEditTaskSetUseCase
import com.rsba.tasks_microservice.domain.usecase.common.custom.task_set.ToExecuteTaskSetUseCase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class TaskSetService(
    private val createOrEditUseCase: CreateOrEditTaskSetUseCase,
    @Qualifier("delete_task_set") private val deleteUseCase: DeleteUseCase<TaskSet>,
    @Qualifier("find_tasks_set") private val findUseCase: FindUseCase<TaskSet>,
    @Qualifier("retrieve_tasks_set") private val retrieveUseCase: RetrieveUseCase<TaskSet>,
    @Qualifier("count_tasks_set") private val countUseCase: CountUseCase,
    @Qualifier("retrieve_task_set_tasks") private val retrieveTasksUseCase: RetrieveTasksUseCase,
    @Qualifier("retrieve_task_set_users") private val retrieveUsersUseCase: RetrieveUsersUseCase,
    @Qualifier("retrieve_task_set_comments") private val retrieveCommentsUseCase: RetrieveCommentsUseCase,
    private val toExecuteTaskSetUseCase: ToExecuteTaskSetUseCase
) : TaskSetRepository {

    override suspend fun toCreateOrEdit(input: TaskSetInput, action: MutationAction?, token: UUID): Optional<TaskSet> =
        createOrEditUseCase(input = input, action = action, token = token)

    override suspend fun toDelete(input: UUID, token: UUID): Boolean =
        deleteUseCase(input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<TaskSet> =
        findUseCase(id = id, token = token)

    override suspend fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?,
        id: UUID?
    ): List<TaskSet> =
        retrieveUseCase(
            first = first,
            after = after,
            token = token,
            id = id,
            rangeStart = rangeStart,
            rangeEnd = rangeEnd,
        )

    override suspend fun search(
        input: String,
        first: Int,
        after: UUID?,
        token: UUID,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?
    ): List<TaskSet> = emptyList()

    override suspend fun count(token: UUID): Int = countUseCase(token = token)

    override suspend fun users(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<User>> =
        retrieveUsersUseCase(ids = ids, first = first, after = after, token = token)

    override suspend fun tasks(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Task>> =
        retrieveTasksUseCase(ids = ids, first = first, after = after, token = token)

    override suspend fun comments(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        layer: CommentLayer?,
        token: UUID
    ): Map<UUID, List<Comment>> =
        retrieveCommentsUseCase(ids = ids, first = first, after = after, token = token, layer = layer)

    override suspend fun toExecute(id: UUID, quantity: Int?, token: UUID): Optional<TaskSet> =
        toExecuteTaskSetUseCase(id = id, quantity = quantity, token = token)

}