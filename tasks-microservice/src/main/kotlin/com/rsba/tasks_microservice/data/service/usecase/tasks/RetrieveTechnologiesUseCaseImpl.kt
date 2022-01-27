package com.rsba.tasks_microservice.data.service.usecase.tasks


import com.rsba.tasks_microservice.data.dao.TechnologyDao
import com.rsba.tasks_microservice.data.service.usecase.queries.TaskQueries
import com.rsba.tasks_microservice.domain.model.Technology
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.custom.task.RetrieveTechnologiesUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component(value = "retrieve_task_technologies")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveTechnologiesUseCaseImpl(private val database: DatabaseClient) : RetrieveTechnologiesUseCase {

    override suspend fun invoke(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Technology>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(
                    TaskQueries.technologies(
                        token = token,
                        id = id,
                        first = first,
                        after = after,
                    )
                ).map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? TechnologyDao?)?.to } ?: emptyList() }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume { throw it }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { entries -> entries.associateBy({ it.key }, { it.value ?: emptyList() }) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { emptyMap() }
}