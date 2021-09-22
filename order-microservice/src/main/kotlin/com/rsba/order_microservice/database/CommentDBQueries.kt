package  com.rsba.order_microservice.database


import com.rsba.order_microservice.domain.input.CommentTaskInput
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object CommentDBQueries {

    fun createOrEditCommentInTaskQuery(input: CommentTaskInput, token: UUID) =
        "SELECT on_create_or_edit_comment_of_task('${Json.encodeToString(input)}', '$token')"

    fun deleteComment(input: UUID, token: UUID) =
        "SELECT on_delete_comment('$input', '$token')"

    fun retrieveCommentByTaskId(taskId: UUID, first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_comments_by_task_id_with_pagination('$taskId','$first', ${after?.let { "'$it'" }},'$token')"

    fun retrieveActorByCommentId(commentId: UUID, token: UUID) =
        "SELECT on_retrieve_user_by_comment_id('$commentId', '$token')"
}
