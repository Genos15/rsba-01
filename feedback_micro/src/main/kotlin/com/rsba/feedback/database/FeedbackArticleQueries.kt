package  com.rsba.feedback.database

import com.rsba.feedback.domain.input.FeedbackArticleInput
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object FeedbackArticleQueries {

    fun createOrEdit(input: FeedbackArticleInput, token: UUID) =
        "SELECT on_create_or_edit_feedback_article('${Json.encodeToString(input)}', '$token')"

    fun delete(input: UUID, token: UUID) =
        "SELECT on_delete_feedback_article('$input', '$token')"

    fun retrieve(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_feedback_article('$first', ${after?.let { "'$it'" }},'$token')"

    fun search(input: String, first: Int, after: UUID?, token: UUID) =
        "SELECT on_search_feedback_article('$input','$first', ${after?.let { "'$it'" }},'$token')"

    fun retrieveById(id: UUID, token: UUID) =
        "SELECT on_retrieve_feedback_article_by_id('$id','$token')"
}
