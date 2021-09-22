package  com.rsba.monitor_ms.repository

import com.rsba.monitor_ms.domain.input.AttachCategoryWithOrderInput
import  com.rsba.monitor_ms.domain.input.CreateOrderInput
import com.rsba.monitor_ms.domain.input.EditCategoryOfOrderInput
import com.rsba.monitor_ms.domain.input.EditOrderInput
import com.rsba.monitor_ms.domain.model.Agent
import com.rsba.monitor_ms.domain.model.CategoryOfItemInOrder
import  com.rsba.monitor_ms.domain.model.Order
import reactor.core.publisher.Flux
import java.util.*

interface OrderRepository {

    suspend fun createOrder(input: CreateOrderInput, token: UUID): Optional<Order>

    suspend fun deleteOrder(input: UUID, token: UUID): Int

    suspend fun onRetrieveAllOrder(token: UUID): MutableList<Order>

    suspend fun retrieveManagerOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Agent?>

    suspend fun retrieveAgentOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Agent?>

    suspend fun retrieveCategoriesOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<CategoryOfItemInOrder>>

    suspend fun editOrder(input: EditOrderInput, token: UUID): Optional<Order>

    suspend fun addCategoriesInOrder(input: AttachCategoryWithOrderInput, token: UUID): Optional<Order>

    suspend fun editCategoryOfOrder(input: EditCategoryOfOrderInput, token: UUID): Optional<Order>

    suspend fun retrieveOneOrder(id: UUID, token: UUID): Optional<Order>

    fun all(token: UUID): Flux<MutableList<Order>>
}
