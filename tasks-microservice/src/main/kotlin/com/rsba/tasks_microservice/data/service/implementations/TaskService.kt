package com.rsba.tasks_microservice.data.service.implementations

import com.rsba.tasks_microservice.domain.input.TaskInput
import com.rsba.tasks_microservice.domain.input.TaskWorkerTimeInput
import com.rsba.tasks_microservice.domain.model.*
import com.rsba.tasks_microservice.domain.repository.TaskRepository
import com.rsba.tasks_microservice.domain.usecase.common.*
import com.rsba.tasks_microservice.domain.usecase.custom.task.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskService(
    private val database: DatabaseClient,
    @Qualifier("create_edit_task") private val createOrEditUseCase: CreateOrEditUseCase<TaskInput, Task>,
    @Qualifier("delete_task") private val deleteUseCase: DeleteUseCase<Task>,
    @Qualifier("find_task") private val findUseCase: FindUseCase<Task>,
    @Qualifier("retrieve_tasks") private val retrieveUseCase: RetrieveUseCase<Task>,
    @Qualifier("search_tasks") private val searchUseCase: SearchUseCase<Task>,
    @Qualifier("count_tasks") private val countUseCase: CountUseCase,
    private val findOrderUseCase: FindOrderUseCase,
    private val findItemUseCase: FindItemUseCase,
    private val findOperationUseCase: FindOperationUseCase,
    private val findWorkcenterUseCase: FindWorkcenterUseCase,
    private val toAllocateUseCase: ToAllocateUseCase,
    private val retrieveUsersUseCase: RetrieveUsersUseCase
) : TaskRepository {

    override suspend fun toEdit(
        input: TaskInput,
        action: MutationAction?,
        token: UUID
    ): Optional<Task> =
        createOrEditUseCase(database = database, input = input, action = action, token = token)

    override suspend fun toDelete(input: UUID, token: UUID): Boolean =
        deleteUseCase(database = database, input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<Task> =
        findUseCase(database = database, id = id, token = token)

    override suspend fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        status: TaskStatus?,
        layer: TaskLayer?,
        id: UUID?
    ): List<Task> =
        retrieveUseCase(
            database = database,
            first = first,
            after = after,
            token = token,
            status = status,
            layer = layer,
            id = id
        )

    override suspend fun search(
        input: String,
        first: Int,
        after: UUID?,
        token: UUID,
        status: TaskStatus?,
        layer: TaskLayer?,
        id: UUID?
    ): List<Task> =
        searchUseCase(
            database = database,
            first = first,
            after = after,
            token = token,
            input = input,
            status = status,
            layer = layer,
            id = id
        )

    override suspend fun count(status: TaskStatus?, layer: TaskLayer?, id: UUID?, token: UUID): Int =
        countUseCase(
            database = database,
            token = token,
            status = status,
            layer = layer,
            id = id
        )

    override suspend fun item(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Item>> =
        findItemUseCase(database = database, ids = ids, token = token)

    override suspend fun operation(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Operation>> =
        findOperationUseCase(database = database, ids = ids, token = token)

    override suspend fun order(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Order>> =
        findOrderUseCase(database = database, ids = ids, token = token)

    override suspend fun workcenter(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Workcenter>> =
        findWorkcenterUseCase(database = database, ids = ids, token = token)

    override suspend fun toAllocate(
        id: UUID,
        users: List<TaskWorkerTimeInput>,
        action: MutationAction?,
        token: UUID
    ): Optional<Task> = toAllocateUseCase(database = database, id = id, action = action, token = token, users = users)

    override suspend fun users(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<User>> =
        retrieveUsersUseCase(database = database, ids = ids, token = token, first = first, after = after)

}