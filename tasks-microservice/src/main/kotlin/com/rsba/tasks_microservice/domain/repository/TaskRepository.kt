package com.rsba.tasks_microservice.domain.repository

import com.rsba.tasks_microservice.domain.input.*
import com.rsba.tasks_microservice.domain.model.*
import java.time.OffsetDateTime
import java.util.*

interface TaskRepository {

    suspend fun toEdit(
        input: TaskInput,
        action: MutationAction? = null,
        token: UUID
    ): Optional<Task>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun toExecuteTasksOfItem(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<Task>

    suspend fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
    ): List<Task>

    suspend fun search(
        input: String,
        first: Int,
        after: UUID? = null,
        token: UUID,
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
    ): List<Task>

    suspend fun count(
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        token: UUID
    ): Int

    suspend fun item(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Item>>

    suspend fun operation(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Operation>>

    suspend fun order(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Order>>

    suspend fun workcenter(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Workcenter>>

    suspend fun toAllocate(
        id: UUID,
        users: List<TaskWorkerTimeInput>,
        action: MutationAction? = null,
        token: UUID = UUID.randomUUID()
    ): Optional<Task>

    suspend fun users(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<User>>

    suspend fun comments(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        layer: CommentLayer? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Comment>>

    suspend fun technologies(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Technology>>

    suspend fun userActivities(
        first: Int,
        after: UUID? = null,
        layer: UserActivityLayer,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        token: UUID
    ): List<User>

    suspend fun userWorkload(
        id: UUID,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        token: UUID
    ): Float

    suspend fun workcenterWorkload(
        id: UUID,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        token: UUID
    ): Float

    suspend fun toExecute(id: UUID, quantity: Int? = null, token: UUID): Optional<Task>

    suspend fun worklogs(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Worklog>>
}