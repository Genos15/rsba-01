package com.rsba.component_microservice.data.service.usecase.technologies

import com.rsba.component_microservice.data.dao.TechnologyDao
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.input.TechnologyInput
import com.rsba.component_microservice.domain.model.Edition
import com.rsba.component_microservice.domain.model.EditionCase
import com.rsba.component_microservice.domain.model.MutationAction
import com.rsba.component_microservice.domain.model.Technology
import com.rsba.component_microservice.domain.queries.IQueryGuesser
import com.rsba.component_microservice.domain.queries.query
import com.rsba.component_microservice.domain.usecase.common.CreateOrEditUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "create_edit_technology")
@OptIn(ExperimentalSerializationApi::class)
class CreateOrEditUseCaseImpl : CreateOrEditUseCase<TechnologyInput, Technology>, IQueryGuesser {
    override suspend fun invoke(
        database: DatabaseClient,
        input: TechnologyInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?,
    ): Optional<Technology> =
        database.sql(query<TechnologyDao>().createOrEdit(input = input, token = token, action = action, case = case))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it as? TechnologyDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}