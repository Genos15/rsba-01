package com.rsba.tasks_microservice.domain.repository

import com.rsba.tasks_microservice.domain.input.*
import com.rsba.tasks_microservice.domain.model.*
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

interface TaskSetRepository {

    suspend fun toCreateOrEdit(
        input: TaskSetInput,
        action: MutationAction? = null,
        token: UUID
    ): Optional<TaskSet>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<TaskSet>

    suspend fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
        id: UUID? = null
    ): List<TaskSet>

    suspend fun search(
        input: String,
        first: Int,
        after: UUID? = null,
        token: UUID,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
    ): List<TaskSet>

    suspend fun count(
        token: UUID
    ): Int

    suspend fun users(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<User>>

    suspend fun tasks(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Task>>

    suspend fun comments(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        layer: CommentLayer? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Comment>>

    suspend fun toExecute(id: UUID, quantity: Int? = null, token: UUID): Optional<TaskSet>

}