package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.domain.input.CreateOrEditWorkingCenterInput
import com.rsba.usermicroservice.domain.input.ManagerInWorkingCenterInput
import com.rsba.usermicroservice.domain.input.UserInWorkingCenterInput
import com.rsba.usermicroservice.domain.input.UserInWorkingCenterPayload
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.domain.model.WorkingCenter
import com.rsba.usermicroservice.query.database.UserDBHandler
import com.rsba.usermicroservice.query.database.WorkingCenterDBHandler
import com.rsba.usermicroservice.query.database.WorkingCenterDBQueries
import com.rsba.usermicroservice.repository.WorkingCenterRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.*
import java.util.stream.Collectors

@Service
class WorkingCenterService(private val database: DatabaseClient) : WorkingCenterRepository {

    override suspend fun createOrEdit(input: CreateOrEditWorkingCenterInput, token: UUID): Optional<WorkingCenter> =
        database.sql(WorkingCenterDBQueries.createOrEdit(input = input, token = token))
            .map { row, meta -> WorkingCenterDBHandler.one(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun delete(input: UUID, token: UUID): Boolean =
        database.sql(WorkingCenterDBQueries.delete(id = input, token = token))
            .map { row, meta -> WorkingCenterDBHandler.count(row = row, meta = meta) }
            .first()
            .map { it > 0 }
            .awaitFirstOrElse { false }

    override suspend fun retrieveAllWorkingCenters(first: Int, after: UUID?, token: UUID): MutableList<WorkingCenter> =
        database.sql(WorkingCenterDBQueries.retrieveAll(first = first, after = after, token = token))
            .map { row, meta -> WorkingCenterDBHandler.all(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { mutableListOf() }

    override suspend fun retrieveWorkingCenterById(id: UUID, token: UUID): Optional<WorkingCenter> =
        database.sql(WorkingCenterDBQueries.retrieveById(id = id, token = token))
            .map { row, meta -> WorkingCenterDBHandler.one(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun addUserInWorkingCenter(input: UserInWorkingCenterInput, token: UUID): Optional<WorkingCenter> =
        Flux.fromIterable(input.users ?: listOf())
            .flatMap { userId ->
                val payload = UserInWorkingCenterPayload(workcenterId = input.id, userId = UUID.fromString(userId))
                database.sql(WorkingCenterDBQueries.addUserInWorkingCenter(input = payload, token = token))
                    .map { row, meta -> WorkingCenterDBHandler.one(row = row, meta = meta) }
                    .first()
            }.collectList()
            .map { it.first() }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun deleteUserInWorkingCenter(
        input: UserInWorkingCenterInput,
        token: UUID
    ): Optional<WorkingCenter> =
        Flux.fromIterable(input.users ?: listOf())
            .flatMap { userId ->
                val payload = UserInWorkingCenterPayload(workcenterId = input.id, userId = UUID.fromString(userId))
                database.sql(WorkingCenterDBQueries.deleteUserInWorkingCenter(input = payload, token = token))
                    .map { row, meta -> WorkingCenterDBHandler.one(row = row, meta = meta) }
                    .first()
            }.collectList()
            .map { it.first() }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun addManagerInWorkingCenter(
        input: ManagerInWorkingCenterInput,
        token: UUID
    ): Optional<WorkingCenter> =
        database.sql(WorkingCenterDBQueries.pickManager(input = input, token = token))
            .map { row, meta -> WorkingCenterDBHandler.one(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun deleteManagerInWorkingCenter(
        input: ManagerInWorkingCenterInput,
        token: UUID
    ): Optional<WorkingCenter> =
        database.sql(WorkingCenterDBQueries.unpickManager(input = input, token = token))
            .map { row, meta -> WorkingCenterDBHandler.one(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun retrieveUserOfWorkingCenter(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<User>> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(
                WorkingCenterDBQueries.retrieveUsersByWorkcenterId(
                    id = id,
                    first = first,
                    after = after,
                    token = token
                )
            ).map { row, meta -> UserDBHandler.all(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }.collect(Collectors.toList())
        .map {
            val map = mutableMapOf<UUID, List<User>>()
            it.forEach { element -> map[element.key] = element.value }
            return@map map.toMap()
        }.awaitFirstOrElse { emptyMap() }

    override suspend fun retrieveManagersOfWorkingCenter(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<User>> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(
                WorkingCenterDBQueries.retrieveManagersByWorkingCenterId(
                    input = id,
                    token = token
                )
            ).map { row, meta -> UserDBHandler.all(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }.collect(Collectors.toList())
        .map {
            val map = mutableMapOf<UUID, List<User>>()
            it.forEach { element -> map[element.key] = element.value }
            return@map map.toMap()
        }.awaitFirstOrElse { emptyMap() }

}