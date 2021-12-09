package  com.rsba.order_microservice.domain.repository

import com.rsba.order_microservice.domain.input.ItemAndItemInput
import com.rsba.order_microservice.domain.model.*
import java.util.*

interface ItemRepository {

    suspend fun myOperations(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Operation>>

    suspend fun myCategory(
        ids: Set<Item>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<Item, ItemCategory?>

    suspend fun myItems(
        ids: Set<Item>,
        userId: UUID
    ): Map<Item, List<Item>>

    suspend fun myTasks(
        ids: Set<Item>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<Item, List<Task>>

    suspend fun myDetails(orderId: UUID, itemId: UUID, token: UUID): Optional<DetailItemInOrder>

    suspend fun myDetailActor(
        ids: Set<DetailItemInOrder>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<DetailItemInOrder, Optional<User>>

    suspend fun myDetailTechnologies(
        ids: Set<DetailItemInOrder>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<DetailItemInOrder, List<Technology>>


    suspend fun addOrEditComponentInItem(input: ItemAndItemInput, token: UUID): Optional<Item>

    suspend fun removeComponentInItem(input: ItemAndItemInput, token: UUID): Optional<Item>

    suspend fun statistics(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<ItemStatistics>>
    suspend fun whoAdded(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<User>>
    suspend fun technologies(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Technology>>

}
