package com.rsba.component_microservice.service

import com.rsba.component_microservice.context.CustomGraphQLContext
import com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.model.Technology
import com.rsba.component_microservice.domain.model.TechnologyFromOld
import com.rsba.component_microservice.query.database.OperationDBHandler2
import com.rsba.component_microservice.query.database.TechnologyDBHandler
import com.rsba.component_microservice.query.database.TechnologyQueries
import com.rsba.component_microservice.repository.TechnologyRepository
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import reactor.core.scheduler.Schedulers
import java.io.File
import java.io.FileReader
import java.util.*

@Service
class TechnologyService(private val database: DatabaseClient, private val logger: KLogger) : TechnologyRepository {

    override suspend fun createOrEdit(input: CreateOrEditTechnologyInput, token: UUID): Optional<Technology> =
        database.sql(TechnologyQueries.createOrEdit(input = input, token = token))
            .map { row, meta -> TechnologyDBHandler.one(row = row, meta = meta) }
            .first()
            .handle { single: Optional<Technology>, sink: SynchronousSink<Optional<Technology>> ->
                if (single.isPresent) {
                    sink.next(single)
                } else {
                    sink.error(RuntimeException("ЧТО-ТО ПОШЛО НЕ ТАК, НЕВОЗМОЖНО СОЗДАТЬ ТЕХНОЛОГИЮ. ПОЖАЛУЙСТА, СВЯЖИТЕСЬ СО СЛУЖБОЙ ПОДДЕРЖКИ"))
                }
            }
            .flatMap { technology ->
                return@flatMap Flux.fromIterable(input.operationIds ?: emptyList())
                    .parallel()
                    .map {
                        TechnologyAndOperation(
                            technologyId = technology.get().id,
                            operationId = UUID.fromString(it)
                        )
                    }
                    .flatMap {
                        database.sql(TechnologyQueries.addOrReorderOperation(input = it, token = token))
                            .map { row, meta -> TechnologyDBHandler.one(row = row, meta = meta) }
                            .first()
                    }
                    .runOn(Schedulers.parallel())
                    .sequential()
                    .collectList()
                    .map { technology }
            }
            .onErrorResume {
                logger.warn { "+TaskService->createOrEdit->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun delete(input: UUID, token: UUID): Boolean =
        database.sql(TechnologyQueries.delete(input = input, token = token))
            .map { row, meta -> TechnologyDBHandler.count(row = row, meta = meta) }
            .first()
            .map { it != null && it > 0 }
            .onErrorResume {
                logger.warn { "+TechnologyService->delete->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { false }

    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Technology> =
        database.sql(TechnologyQueries.retrieve(first = first, after = after, token = token))
            .map { row, meta -> TechnologyDBHandler.all(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+TechnologyService->retrieve->error=${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyList() }

    override suspend fun retrieveById(id: UUID, token: UUID): Optional<Technology> =
        database.sql(TechnologyQueries.retrieveById(input = id, token = token))
            .map { row, meta -> TechnologyDBHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+TechnologyService->retrieveById->error=${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun unpinOperation(input: List<TechnologyAndOperation>, token: UUID): Optional<Technology> =
        Flux.fromIterable(input)
            .parallel()
            .flatMap {
                database.sql(TechnologyQueries.unpinOperation(input = it, token = token))
                    .map { row, meta -> TechnologyDBHandler.one(row = row, meta = meta) }
                    .first()
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { it.firstOrNull() ?: Optional.empty() }
            .onErrorResume {
                logger.warn { "+TaskService->createOrEdit->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun search(content: String, token: UUID): List<Technology> =
        database.sql(TechnologyQueries.search(content = content, token = token))
            .map { row, meta -> TechnologyDBHandler.all(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+TechnologyService->retrieve->error=${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyList() }

    override suspend fun myOperations(ids: Set<UUID>, userId: UUID, page: Int, size: Int): Map<UUID, List<Operation>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                return@flatMap database.sql(TechnologyQueries.myOperations(technologyId = id))
                    .map { row -> OperationDBHandler2.all(row = row) }
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

    override suspend fun importTechnologyFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> =
        Flux.just(environment)
            .map {
                val context: CustomGraphQLContext = it.getContext()
                if (context.fileParts.isEmpty() || context.fileParts.firstOrNull() == null) {
                    throw RuntimeException("FILE NOT FOUND")
                }
                val part = context.fileParts.first()
                val file = File("filename")
                file.writeBytes(part.inputStream.readBytes())
                val reader = FileReader(file)
                val jsonParser = JSONParser()
                val obj: Any = jsonParser.parse(reader)
                val list: List<TechnologyFromOld> = (obj as JSONArray).map { emp ->
                    TechnologyFromOld(
                        name = (emp as JSONObject)["name"] as String,
                        description = emp["description"] as String?
                    )
                }

                Flux.fromIterable(list)
            }
            .parallel()
            .flatMap {
                it.flatMap { elt ->
                    database.sql(TechnologyQueries.addOldTechnology(input = elt, token = UUID.randomUUID()))
                        .map { row -> row }
                        .first()
                }
            }
            .runOn(Schedulers.parallel())
            .map {
                Optional.of(true)
            }.awaitFirstOrElse { Optional.of(false) }

}