package  com.rsba.order_microservice.database


import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.model.OrderAndItem
import java.util.*

object ItemDBQueries {
    fun retrieveMyOperations(itemId: UUID) =
        "SELECT on_retrieve_operations_by_item_id('$itemId')"

    fun retrieveMyTasks(instance: Item) =
        "SELECT on_retrieve_tasks_by_item_id('${instance.id}', '${instance.orderId}')"

    fun retrieveMyCategory(input: Item) =
        "SELECT on_retrieve_category_by_item_id('${input.id}', '${input.orderId}')"

    fun myDetails(orderId: UUID, itemId: UUID, token: UUID) =
        "SELECT on_retrieve_detail_item_in_order_id('$itemId', '$orderId', '$token')"

    fun myDetailActor(input: OrderAndItem) =
        "SELECT on_retrieve_user_item_in_order_id('${input.itemId}', '${input.orderId}')"

    fun myDetailTechnologies(input: OrderAndItem) =
        "SELECT on_retrieve_technologies_item_in_order_id('${input.itemId}', '${input.orderId}')"
}
