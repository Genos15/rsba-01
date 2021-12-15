package com.rsba.order_microservice.data.service.implementation.parameters

import com.rsba.order_microservice.database.ParameterDBHandler
import com.rsba.order_microservice.database.ParameterQueries
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*
import com.rsba.order_microservice.domain.model.Parameter


@Component
class GetParameterByIdUseCase {

    suspend operator fun invoke(database: DatabaseClient, id: UUID, token: UUID): Optional<Parameter> =
        database.sql(ParameterQueries.retrieveById(id = id, token = UUID.randomUUID()))
            .map { row -> ParameterDBHandler.one(row = row) }
            .first()
            .onErrorResume {
                throw it
            }
            .log()
            .awaitFirstOrElse { Optional.empty() }

}