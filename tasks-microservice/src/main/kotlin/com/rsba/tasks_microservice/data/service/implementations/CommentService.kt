package com.rsba.tasks_microservice.data.service.implementations

import com.rsba.tasks_microservice.domain.input.CommentInput
import com.rsba.tasks_microservice.domain.model.*
import com.rsba.tasks_microservice.domain.repository.CommentRepository
import com.rsba.tasks_microservice.domain.usecase.common.custom.comment.CountCommentUseCase
import com.rsba.tasks_microservice.domain.usecase.common.custom.comment.CreateOrEditCommentUseCase
import com.rsba.tasks_microservice.domain.usecase.common.custom.comment.FindReporterUseCase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*
import com.rsba.tasks_microservice.domain.usecase.common.DeleteUseCase

@Service
class CommentService(
    @Qualifier("create_edit_comment") private val createOrEditUseCase: CreateOrEditCommentUseCase,
    @Qualifier("delete_comment") private val deleteUseCase: DeleteUseCase<Comment>,
//    @Qualifier("find_task") private val findUseCase: FindUseCase<Task>,
//    @Qualifier("retrieve_tasks") private val retrieveUseCase: RetrieveUseCase<Task>,
//    @Qualifier("search_tasks") private val searchUseCase: SearchUseCase<Task>,
    private val countCommentUseCase: CountCommentUseCase,
//    private val findOrderUseCase: FindOrderUseCase,
//    private val findItemUseCase: FindItemUseCase,
//    private val findOperationUseCase: FindOperationUseCase,
    private val findReporterUseCase: FindReporterUseCase,
//    private val toAllocateUseCase: ToAllocateUseCase,
//    private val retrieveUsersUseCase: RetrieveUsersUseCase
) : CommentRepository {
    override suspend fun toCreateOrEdit(input: CommentInput, layer: CommentLayer?, token: UUID): Optional<Comment> =
        createOrEditUseCase(input = input, layer = layer, token = token)

    override suspend fun toDelete(input: UUID, token: UUID): Boolean =
        deleteUseCase(input = input, token)

    override suspend fun find(id: UUID, token: UUID): Optional<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        id: UUID?,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?
    ): List<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun search(
        input: String,
        first: Int,
        after: UUID?,
        token: UUID,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?
    ): List<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun count(hostId: UUID, layer: CommentLayer?, token: UUID): Int =
        countCommentUseCase(hostId = hostId, layer = layer, token = token)

    override suspend fun user(ids: Set<UUID>, token: UUID): Map<UUID, Optional<User>> =
        findReporterUseCase(ids = ids, token = token)

}