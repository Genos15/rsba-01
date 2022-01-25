package com.rsba.tasks_microservice.domain.usecase.common

import com.rsba.tasks_microservice.domain.model.Comment
import com.rsba.tasks_microservice.domain.model.CommentLayer
import java.util.*

interface RetrieveCommentsUseCase {
    suspend operator fun invoke(
        ids: Set<UUID>,
        first: Int,
        after: UUID? = null,
        layer: CommentLayer? = null,
        token: UUID
    ): Map<UUID, List<Comment>>
}