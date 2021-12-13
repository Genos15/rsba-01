package  com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.ItemRepository
import com.rsba.order_microservice.domain.usecase.custom.item.FindItemStatisticsUseCase
import com.rsba.order_microservice.domain.usecase.custom.item.FindWhoAddedUseCase
import com.rsba.order_microservice.domain.usecase.custom.item.RetrieveItemElkGraphUseCase
import com.rsba.order_microservice.domain.usecase.custom.item.RetrieveTechnologiesUseCase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemService(
    private val database: DatabaseClient,
    private val findItemStatisticsUseCase: FindItemStatisticsUseCase,
    private val findWhoAddedUseCase: FindWhoAddedUseCase,
    @Qualifier("retrieve_technologies_item") private val retrieveTechnologiesUseCase: RetrieveTechnologiesUseCase,
    private val retrieveItemElkGraphUseCase: RetrieveItemElkGraphUseCase
) : ItemRepository {

    override suspend fun statistics(ids: Set<UUID>, token: UUID): Map<UUID, Optional<ItemStatistics>> =
        findItemStatisticsUseCase(ids = ids, token = token, database = database)

    override suspend fun whoAdded(ids: Set<UUID>, token: UUID): Map<UUID, Optional<User>> =
        findWhoAddedUseCase(ids = ids, token = token, database = database)

    override suspend fun technologies(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Technology>> =
        retrieveTechnologiesUseCase(database = database, first = first, after = after, token = token, ids = ids)

    override suspend fun elk(token: UUID, from: UUID?, height: Int, width: Int): ElkGraph<ElkGraphItemNode> =
        retrieveItemElkGraphUseCase(database = database, token = token, from = from, height = height, width = width)

}