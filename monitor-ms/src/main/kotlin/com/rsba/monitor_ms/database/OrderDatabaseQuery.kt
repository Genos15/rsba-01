package  com.rsba.monitor_ms.database

import com.rsba.monitor_ms.domain.input.AddCategoryInOrderInput
import  com.rsba.monitor_ms.domain.input.CreateOrderInput
import com.rsba.monitor_ms.domain.input.EditCategoryOfOrderInput
import com.rsba.monitor_ms.domain.input.EditOrderInput
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.util.*


@Component
class OrderDatabaseQuery {

    fun onCreateOrder(input: CreateOrderInput, token: UUID) =
        "SELECT on_create_order('${Json.encodeToString(input)}', '$token')"

    fun onDeleteOrder(input: UUID, token: UUID) =
        "SELECT on_delete_order('$input', '$token')"

    fun onRetrieveAllOrder(token: UUID) =
        "SELECT on_retrieve_orders('$token')"

    fun onEditOrder(input: EditOrderInput, token: UUID) =
        "SELECT on_edit_order('${Json.encodeToString(input)}', '$token')"

    fun onAddCategoriesInOrder(input: AddCategoryInOrderInput, token: UUID) =
        "SELECT on_add_category_in_order('${Json.encodeToString(input)}', '$token')"

    fun onEditCategoryOfOrder(input: EditCategoryOfOrderInput, token: UUID) =
        "SELECT on_edit_category_in_order('${Json.encodeToString(input)}', '$token')"

    fun onRetrieveAgentOfOrder(input: UUID) =
        "SELECT on_retrieve_agent_by_order_id('$input')"

    fun onRetrieveManagerOfOrder(input: UUID) =
        "SELECT on_retrieve_manager_by_order_id('$input')"

    fun onRetrieveCategoriesOfOrder(input: UUID) =
        "SELECT on_retrieve_categories_by_order_id('$input')"

    fun onRetrieveOneOrder(input: UUID, token: UUID) =
        "SELECT on_retrieve_order_by_id('$input', '$token')"
}
