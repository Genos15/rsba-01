package com.rsba.tasks_microservice.domain.usecase.common.custom.comment

import com.rsba.tasks_microservice.domain.input.CommentInput
import com.rsba.tasks_microservice.domain.model.Comment
import com.rsba.tasks_microservice.domain.model.CommentLayer
import java.util.*

interface CreateOrEditCommentUseCase {
    suspend operator fun invoke(
        input: CommentInput,
        layer: CommentLayer? = null,
        token: UUID,
    ): Optional<Comment>
}