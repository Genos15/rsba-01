package  com.rsba.order_microservice.domain.repository

import com.rsba.order_microservice.domain.model.*
import java.util.*

interface ItemRepository {

    suspend fun statistics(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<ItemStatistics>>

    suspend fun whoAdded(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<User>>

    suspend fun technologies(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Technology>>

    suspend fun elk(
        token: UUID,
        from: UUID? = null,
        orderId: UUID? = null,
        height: Int,
        width: Int,
    ): ElkGraph<ElkGraphItemNode>

    suspend fun parameters(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Parameter>>

}
