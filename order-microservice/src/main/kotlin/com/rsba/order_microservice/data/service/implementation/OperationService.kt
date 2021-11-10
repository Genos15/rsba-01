package  com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.database.GroupDBHandler
import com.rsba.order_microservice.database.OperationDBQuery
import com.rsba.order_microservice.domain.model.Group
import com.rsba.order_microservice.domain.repository.OperationRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.*
import java.util.stream.Collectors

@Service
class OperationService(private val database: DatabaseClient) : OperationRepository {
    override suspend fun myGroups(ids: Set<UUID>, userId: UUID, page: Int, size: Int): Map<UUID, List<Group>> =
        Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(OperationDBQuery.onRetrieveGroupsByOperationId(operationId = id))
                    .map { row, meta -> GroupDBHandler.all(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<UUID, List<Group>>()
                it.forEach { element -> map[element.key] = element.value ?: emptyList() }
                return@map map.toMap()
            }
            .awaitFirstOrElse { emptyMap() }

}
