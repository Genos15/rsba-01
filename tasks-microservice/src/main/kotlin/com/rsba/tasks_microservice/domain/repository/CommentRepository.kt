package com.rsba.tasks_microservice.domain.repository

import com.rsba.tasks_microservice.domain.input.*
import com.rsba.tasks_microservice.domain.model.*
import java.time.OffsetDateTime
import java.util.*

interface CommentRepository {

    suspend fun toCreateOrEdit(
        input: CommentInput,
        layer: CommentLayer? = null,
        token: UUID
    ): Optional<Comment>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<Comment>

    suspend fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        id: UUID? = null,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
    ): List<Comment>

    suspend fun search(
        input: String,
        first: Int,
        after: UUID? = null,
        token: UUID,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
    ): List<Comment>

    suspend fun count(
        hostId: UUID,
        layer: CommentLayer? = null,
        token: UUID
    ): Int

    suspend fun user(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<User>>


}