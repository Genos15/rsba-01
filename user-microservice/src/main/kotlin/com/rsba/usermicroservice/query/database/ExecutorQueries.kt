package  com.rsba.usermicroservice.query.database

import com.rsba.usermicroservice.domain.input.ExecutorInput
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object ExecutorQueries {

    fun createOrEdit(input: ExecutorInput, token: UUID) =
        "SELECT on_create_or_edit_executor('${Json.encodeToString(input)}', '$token')"

    fun renewPin(input: ExecutorInput, token: UUID) =
        "SELECT on_renew_executor_pin('${Json.encodeToString(input)}', '$token')"

    fun delete(input: UUID, token: UUID) =
        "SELECT on_delete_executor('$input', '$token')"

    fun retrieve(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_executors('$first', ${after?.let { "'$it'" }},'$token')"

    fun search(input: String, first: Int, after: UUID?, token: UUID) =
        "SELECT on_search_executors('$input','$first', ${after?.let { "'$it'" }},'$token')"

    fun retrieveById(id: UUID, token: UUID) =
        "SELECT on_retrieve_executor_by_id('$id','$token')"
}
