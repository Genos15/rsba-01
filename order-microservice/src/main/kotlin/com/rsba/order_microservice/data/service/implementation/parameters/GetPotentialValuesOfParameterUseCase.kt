package com.rsba.order_microservice.data.service.implementation.parameters

import com.rsba.order_microservice.database.ParameterPotentialValueDBHandler
import com.rsba.order_microservice.database.ParameterQueries
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactor.mono
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
class GetPotentialValuesOfParameterUseCase {

    suspend operator fun invoke(database: DatabaseClient, ids: Set<UUID>): Map<UUID, List<String>> = mono {
        val data = Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                return@flatMap database.sql(ParameterQueries.myPotentialValues(id = id, token = UUID.randomUUID()))
                    .map { row -> ParameterPotentialValueDBHandler.all(row = row) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it.map { param -> param.value }.toList()) }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map {
                val map = mutableMapOf<UUID, List<String>>()
                it.forEach { element -> map[element.key] = element.value ?: emptyList() }
                return@map map.toMap()
            }
            .onErrorResume {
                throw it
            }
            .log()
            .awaitFirstOrElse { emptyMap() }

        data
    }.log().awaitFirst()

}