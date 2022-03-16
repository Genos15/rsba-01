package com.rsba.tasks_microservice.domain.usecase.common.custom.comment

import com.rsba.tasks_microservice.domain.model.CommentLayer
import java.util.*

interface CountCommentUseCase {
    suspend operator fun invoke(
        hostId: UUID,
        layer: CommentLayer? = null,
        token: UUID
    ): Int
}