package com.rsba.component_microservice.data.service.usecase.technologies

import com.rsba.component_microservice.data.dao.TechnologyDao
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.model.Technology
import com.rsba.component_microservice.domain.queries.IQueryGuesser
import com.rsba.component_microservice.domain.queries.query
import com.rsba.component_microservice.domain.usecase.common.SearchUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Component(value = "search_technology")
class SearchUseCaseImpl : SearchUseCase<Technology>, IQueryGuesser {
    override suspend fun invoke(
        database: DatabaseClient,
        input: String,
        first: Int,
        after: UUID?,
        token: UUID
    ): List<Technology> =
        database.sql(query<TechnologyDao>().search(input = input, token = token, first = first, after = after))
            .map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? TechnologyDao?)?.to } ?: emptyList() }
            .onErrorResume {
                throw it
            }
            .awaitFirstOrElse { emptyList() }
}