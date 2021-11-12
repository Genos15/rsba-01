package com.rsba.component_microservice.data.service.usecase.technologies

import com.rsba.component_microservice.data.dao.TechnologyDao
import com.rsba.component_microservice.domain.exception.CustomGraphQLError
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.model.Technology
import com.rsba.component_microservice.domain.queries.IQueryGuesser
import com.rsba.component_microservice.domain.queries.query
import com.rsba.component_microservice.domain.usecase.common.FindUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component(value = "find_technology")
@OptIn(ExperimentalSerializationApi::class)
class FindUseCaseImpl : FindUseCase<Technology>, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, id: UUID, token: UUID): Optional<Technology> =
        database.sql(query<TechnologyDao>().find(id = id, token = token))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it as? TechnologyDao?)?.to) }
            .onErrorResume {
                if (it is CustomGraphQLError) {
                    Mono.empty()
                } else {
                    throw it
                }
            }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}