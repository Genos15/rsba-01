package  com.rsba.order_microservice.database


import com.rsba.order_microservice.domain.input.EditItemInOrderInput
import com.rsba.order_microservice.domain.input.ItemInOrderInput
import com.rsba.order_microservice.domain.model.OrderFromOld
import com.rsba.order_microservice.domain.model.OrderLevel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object OrderDBQueries {
    fun addItemInOrder(input: ItemInOrderInput, token: UUID) =
        "SELECT on_add_item_in_order('${Json.encodeToString(input)}', '$token')"

    fun editItemInOrder(input: EditItemInOrderInput, token: UUID) =
        "SELECT on_edit_item_in_order('${Json.encodeToString(input)}', '$token')"

    fun deleteItemInOrder(input: ItemInOrderInput, token: UUID) =
        "SELECT on_delete_item_in_order('${Json.encodeToString(input)}', '$token')"

    fun retrieveMyItems(orderId: UUID) =
        "SELECT on_retrieve_items_by_order_id('$orderId')"

    fun retrieveOnItemInOrder(itemId: UUID, orderId: UUID, token: UUID) =
        "SELECT on_retrieve_item_in_order_by_id('$itemId', '$orderId', '$token')"

    fun terminateAnalysis(input: UUID, token: UUID) =
        "SELECT on_terminate_order_analysis('$input', '$token')"

    fun retrieveProgressionStepByOrderId(orderId: UUID, token: UUID) =
        "SELECT on_retrieve_progression_steps_by_order_id('$orderId', '$token')"

    fun addOldOrder(input: OrderFromOld, token: UUID) =
        "SELECT on__add__old__order('${Json.encodeToString(input)}', '$token')"

    fun retrieveNumberOfActiveOrder(token: UUID) =
        "SELECT on_retrieve_number_of_active_order( '$token')"

    fun retrieveNextOrderReference(companyId: UUID, token: UUID) =
        "SELECT util_next_order_reference('$companyId', '$token')"

    fun retrieveMyType(orderId: UUID, token: UUID) =
        "SELECT on_retrieve_order_type_by_order_id('$orderId', '$token')"

    fun retrieveOrderByUserToken(first: Int, after: UUID?, token: UUID, level: OrderLevel? = null) =
        "SELECT on_retrieve_orders_by_user_token('$first', ${after?.let { "'$it'" }}, ${level?.let { "'$it'" }},'$token')"

    fun completedOrder(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_orders_completed('$first', ${after?.let { "'$it'" }},'$token')"

    fun ordersByDepartmentId(departmentId: UUID, first: Int, after: UUID?, token: UUID, level: OrderLevel? = null) =
        "SELECT on_retrieve_orders_by_department_id('$departmentId', '$first', ${after?.let { "'$it'" }}, ${level?.let { "'$it'" }},'$token')"


}
