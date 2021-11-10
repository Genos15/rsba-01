package com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.database.ItemDBHandler
import com.rsba.order_microservice.database.OperationDBHandler
import com.rsba.order_microservice.database.TaskDBHandler
import com.rsba.order_microservice.database.TechnologyQueries
import com.rsba.order_microservice.domain.input.FindItemAttachedToTechnologyInput
import com.rsba.order_microservice.domain.input.FindOperationAttachedToTechnologyInput
import com.rsba.order_microservice.domain.input.FindTaskAttachedToTechnologyInput
import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.model.Operation
import com.rsba.order_microservice.domain.model.Task
import com.rsba.order_microservice.domain.repository.TechnologyRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Service
class TechnologyService(private val database: DatabaseClient, private val logger: KLogger) : TechnologyRepository {

    override suspend fun myOperations(ids: Set<UUID>, userId: UUID, page: Int, size: Int): Map<UUID, List<Operation>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                return@flatMap database.sql(TechnologyQueries.myOperations(technologyId = id))
                    .map { row -> OperationDBHandler.all(row = row) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map {
                val map = mutableMapOf<UUID, List<Operation>>()
                it.forEach { element -> map[element.key] = element.value ?: emptyList() }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+TechnologyService->myOperations->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun operationsAttachedTechnologyInOrder(
        input: FindOperationAttachedToTechnologyInput,
        token: UUID
    ): List<Operation> = Flux.fromIterable(input.technologiesIds ?: emptyList())
        .parallel()
        .map { UUID.fromString(it) }
        .flatMap {
            return@flatMap database.sql(
                TechnologyQueries.operationAttachedInOrder(
                    technologyId = it,
                    orderId = input.orderId,
                    token = token
                )
            ).map { row -> OperationDBHandler.all(row = row) }.first()
        }
        .runOn(Schedulers.parallel())
        .sequential()
        .collectList()
        .map { it.flatten() }
        .onErrorResume {
            logger.warn { "+TechnologyService->operationsAttachedTechnologyInOrder->error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyList() }

    override suspend fun tasksAttachedTechnologyInOrder(
        input: FindTaskAttachedToTechnologyInput,
        token: UUID
    ): List<Task> = Flux.fromIterable(input.technologiesIds ?: emptyList())
        .parallel()
        .map { UUID.fromString(it) }
        .flatMap {
            return@flatMap database.sql(
                TechnologyQueries.taskAttachedInOrder(
                    technologyId = it,
                    orderId = input.orderId,
                    token = token
                )
            ).map { row -> TaskDBHandler.all(row = row) }.first()
        }
        .runOn(Schedulers.parallel())
        .sequential()
        .collectList()
        .map { it.flatten() }
        .onErrorResume {
            logger.warn { "+TechnologyService->tasksAttachedTechnologyInOrder->error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyList() }

    override suspend fun itemsAttachedTechnologyInOrder(
        input: FindItemAttachedToTechnologyInput,
        token: UUID
    ): List<Item> =
        Flux.fromIterable(input.technologiesIds ?: emptyList())
            .parallel()
            .map { UUID.fromString(it) }
            .flatMap {
                return@flatMap database.sql(
                    TechnologyQueries.itemAttachedInOrder(
                        technologyId = it,
                        orderId = input.orderId,
                        token = token
                    )
                ).map { row -> ItemDBHandler.all(row = row) }.first()
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { it.flatten() }
            .onErrorResume {
                logger.warn { "+TechnologyService->itemsAttachedTechnologyInOrder->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyList() }

}