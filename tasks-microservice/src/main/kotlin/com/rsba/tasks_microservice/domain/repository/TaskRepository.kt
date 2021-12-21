package com.rsba.tasks_microservice.domain.repository

import com.rsba.tasks_microservice.domain.input.*
import com.rsba.tasks_microservice.domain.model.MutationAction
import com.rsba.tasks_microservice.domain.model.Task
import com.rsba.tasks_microservice.domain.model.TaskLayer
import com.rsba.tasks_microservice.domain.model.TaskStatus
import java.util.*

interface TaskRepository {

    suspend fun toEdit(
        input: TaskInput,
        action: MutationAction? = null,
        token: UUID
    ): Optional<Task>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<Task>

    suspend fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null
    ): List<Task>

    suspend fun search(
        input: String,
        first: Int,
        after: UUID? = null,
        token: UUID,
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null
    ): List<Task>

    suspend fun count(
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        token: UUID
    ): Int

}