package  com.rsba.order_microservice.database

import com.rsba.order_microservice.domain.input.OrderTypeInput
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object OrderTypeQueries {

    fun createOrEdit(input: OrderTypeInput, token: UUID) =
        "SELECT on_create_or_edit_order_type('${Json.encodeToString(input)}', '$token')"

    fun delete(input: UUID, token: UUID) =
        "SELECT on_delete_order_type('$input', '$token')"

    fun retrieve(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_order_type('$first', ${after?.let { "'$it'" }},'$token')"

    fun search(input: String, first: Int, after: UUID?, token: UUID) =
        "SELECT on_search_order_type('$input','$first', ${after?.let { "'$it'" }},'$token')"

    fun retrieveById(id: UUID, token: UUID) =
        "SELECT on_retrieve_order_type_by_id('$id','$token')"
}
