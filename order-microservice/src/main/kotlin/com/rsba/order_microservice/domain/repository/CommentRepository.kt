package  com.rsba.order_microservice.domain.repository

import com.rsba.order_microservice.domain.input.CommentTaskInput
import com.rsba.order_microservice.domain.model.*
import java.util.*

interface CommentRepository {
    suspend fun createOrEditComment(input: CommentTaskInput, token: UUID): Optional<Comment>
    suspend fun deleteComment(input: UUID, token: UUID): Boolean
    suspend fun retrieveCommentsByTaskId(taskId: UUID, first: Int, after: UUID?, token: UUID): List<Comment>

    suspend fun myActor(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, Optional<User>>
}
