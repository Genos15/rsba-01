package com.rsba.component_microservice.data.service.implementations

import com.rsba.component_microservice.data.context.CustomGraphQLContext
import com.rsba.component_microservice.database.GroupDBHandler
import com.rsba.component_microservice.database.OperationDBHandler
import com.rsba.component_microservice.database.OperationDBQuery
import com.rsba.component_microservice.database.OperationQueries
import com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.Group
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.model.OperationFromOld
import com.rsba.component_microservice.domain.repository.OperationRepository
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono
import java.io.File
import java.io.FileReader
import java.util.*
import java.util.stream.Collectors

@Service
class OperationService(
    private val database: DatabaseClient,
    private val queryHelper: OperationDBQuery,
    private val dataHandler: OperationDBHandler,
    private val groupDataHandler: GroupDBHandler
) : OperationRepository {

    override suspend fun createOrEditOperation(input: CreateOrEditOperationInput, token: UUID): Optional<Operation> =
        database.sql(queryHelper.onCreateOrEditOperation(input = input, token = token))
            .map { row, meta -> dataHandler.oneOperation(row = row, meta = meta) }
            .first()
            .flatMap { op ->
                if (op.isPresent && (input.departments?.size ?: 0) > 0 && input.id == null) {
                    return@flatMap Flux.fromIterable(input.departments!!)
                        .flatMap {
                            val payload =
                                OperationAndGroupInput(operationId = op.get().id, groupId = UUID.fromString(it))
                            database.sql(queryHelper.onAttachOperationToDepartment(input = payload, token = token))
                                .map { row, meta -> dataHandler.oneOperation(row = row, meta = meta) }
                                .first()
                        }.map { op }
                        .toMono()
                } else {
                    return@flatMap Mono.just(op)
                }
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun deleteOperation(input: UUID, token: UUID): Int =
        database.sql(queryHelper.onDeleteOperation(input = input, token = token))
            .map { row, meta -> dataHandler.count(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { 0 }

    override suspend fun retrieveAllOperation(first: Int, after: UUID?, token: UUID): MutableList<Operation> =
        database.sql(queryHelper.onRetrieveAllOperation(first = first, after = after, token = token))
            .map { row, meta -> dataHandler.all(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { mutableListOf() }

    override suspend fun attachOperationToGroup(input: OperationAndGroupInput, token: UUID): Optional<Operation> =
        database.sql(queryHelper.onAttachOperationToDepartment(input = input, token = token))
            .map { row, meta -> dataHandler.oneOperation(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun detachOperationToGroup(input: OperationAndGroupInput, token: UUID): Optional<Operation> =
        database.sql(queryHelper.onDetachOperationToDepartment(input = input, token = token))
            .map { row, meta -> dataHandler.oneOperation(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun retrieveGroupInOperation(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, MutableList<Group>> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(queryHelper.onRetrieveGroupsByOperationId(operationId = id))
                .map { row, meta -> groupDataHandler.all(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }
        .collect(Collectors.toList())
        .map {
            val map = mutableMapOf<UUID, MutableList<Group>>()
            it.forEach { element -> map[element.key] = element.value }
            return@map map.toMap()
        }
        .awaitFirstOrElse { emptyMap() }

    override suspend fun importOperationFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> =
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
                val list: List<OperationFromOld> = (obj as JSONArray).map { emp ->
                    OperationFromOld(
                        name = (emp as JSONObject)["name"] as String,
                        description = emp["description"] as? String?,
                        time = emp["time"] as? Int?
                    )
                }
                Flux.fromIterable(list)
            }
            .parallel()
            .flatMap {
                it.flatMap { elt ->
                    database.sql(OperationQueries.addOldOperation(input = elt, token = UUID.randomUUID()))
                        .map { row -> row }
                        .first()
                }
            }
            .runOn(Schedulers.parallel())
            .map {
                Optional.of(true)
            }.awaitFirstOrElse { Optional.of(false) }

}