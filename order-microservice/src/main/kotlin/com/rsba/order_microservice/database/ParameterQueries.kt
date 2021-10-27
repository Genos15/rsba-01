package  com.rsba.order_microservice.database


import com.rsba.order_microservice.domain.input.ParameterInput
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
object ParameterQueries {

    fun createOrEdit(input: ParameterInput, token: UUID) =
        "SELECT on_create_or_edit_parameter('${Json.encodeToString(input)}', '$token')"

    fun addOrRemovePotentialValue(input: ParameterInput, token: UUID) =
        "SELECT on_add_or_remove_value_parameter('${Json.encodeToString(input)}', '$token')"

    fun delete(input: UUID, token: UUID) =
        "SELECT on_delete_parameter('$input', '$token')"

    fun retrieve(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_parameters('$first', ${after?.let { "'$it'" }},'$token')"

    fun search(input: String, first: Int, after: UUID?, token: UUID) =
        "SELECT on_search_parameters('$input', '$first', ${after?.let { "'$it'" }},'$token')"

    fun retrieveByTaskId(taskId: UUID, token: UUID) =
        "SELECT on_retrieve_parameter_by_task_id('$taskId', '$token')"

    fun retrieveById(id: UUID, token: UUID) =
        "SELECT on_retrieve_parameter_by_id('$id', '$token')"

    fun retrieveByItemId(itemId: UUID, token: UUID) =
        "SELECT on_retrieve_parameter_by_item_id('$itemId', '$token')"

    fun myPotentialValues(id: UUID, token: UUID) =
        "SELECT on_retrieve_potential_value_parameter_by_id('$id', '$token')"
}
