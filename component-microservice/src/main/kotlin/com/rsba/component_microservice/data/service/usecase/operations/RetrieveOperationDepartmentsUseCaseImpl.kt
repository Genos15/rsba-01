package com.rsba.component_microservice.data.service.usecase.operations

import com.rsba.component_microservice.data.dao.DepartmentDao
import com.rsba.component_microservice.data.service.usecase.queries.OperationQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.model.Group
import com.rsba.component_microservice.domain.usecase.custom.operation.RetrieveOperationDepartmentsUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveOperationDepartmentsUseCaseImpl : RetrieveOperationDepartmentsUseCase {

    override suspend fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Group>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(OperationQueries.departments(token = token, id = id, first = first, after = after))
                    .map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? DepartmentDao?)?.to } ?: emptyList() }
                    .doOnNext { println(it) }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume { throw it }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { entries -> entries.associateBy({ it.key }, { it.value ?: emptyList() }) }
            .onErrorResume { throw it }
            .log("RetrieveOperationDepartmentsUseCase")
            .awaitFirstOrElse { emptyMap() }
}