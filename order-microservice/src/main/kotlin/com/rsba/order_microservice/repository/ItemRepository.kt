package  com.rsba.order_microservice.repository

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
    ): Map<Item, CategoryOfItem?>

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
}
