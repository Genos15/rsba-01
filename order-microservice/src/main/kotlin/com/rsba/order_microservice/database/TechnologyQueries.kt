package  com.rsba.order_microservice.database


import java.util.*

object TechnologyQueries {

    fun delete(input: UUID, token: UUID) =
        "SELECT on_delete_technology('$input', '$token')"

    fun retrieveById(input: UUID, token: UUID) =
        "SELECT on_retrieve_technology_by_id('$input', '$token')"

    fun retrieve(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_technologies('$first', ${after?.let { "'$it'" }},'$token')"

    fun search(content: String, token: UUID) =
        "SELECT on_search_technologies('$content','$token')"

    fun myOperations(technologyId: UUID) =
        "SELECT on_retrieve_operation_by_technology_id('$technologyId')"

    fun operationAttachedInOrder(technologyId: UUID, orderId: UUID, token: UUID) =
        "SELECT on_retrieve_operation_in_order_technology_id('$technologyId', '$orderId', '$token')"

    fun taskAttachedInOrder(technologyId: UUID, orderId: UUID, token: UUID) =
        "SELECT on_retrieve_task_in_order_technology_id('$technologyId', '$orderId', '$token')"

    fun itemAttachedInOrder(technologyId: UUID, orderId: UUID, token: UUID) =
        "SELECT on_retrieve_item_in_order_technology_id('$technologyId', '$orderId', '$token')"
}
