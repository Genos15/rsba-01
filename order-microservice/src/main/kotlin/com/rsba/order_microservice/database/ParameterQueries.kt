package  com.rsba.order_microservice.database


import com.rsba.order_microservice.domain.input.ParameterInput
import java.util.*

object ParameterQueries {

    fun createOrEdit(input: ParameterInput, token: UUID) =
        "SELECT on_create_or_edit_parameter('$input', '$token')"

    fun addOrRemovePotentialValue(input: ParameterInput, token: UUID) =
        "SELECT on_add_or_remove_value_parameter('$input', '$token')"

    fun delete(input: UUID, token: UUID) =
        "SELECT on_delete_parameter('$input', '$token')"

    fun retrieve(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_parameters('$first', ${after?.let { "'$it'" }},'$token')"

    fun search(input: String, first: Int, after: UUID?, token: UUID) =
        "SELECT on_search_parameters('$first', ${after?.let { "'$it'" }},'$token')"

    fun retrieveByTaskId(taskId: UUID, token: UUID) =
        "SELECT on_retrieve_parameter_by_task_id('$taskId', '$token')"

    fun retrieveByItemId(itemId: UUID, token: UUID) =
        "SELECT on_retrieve_parameter_by_item_id('$itemId', '$token')"
}
