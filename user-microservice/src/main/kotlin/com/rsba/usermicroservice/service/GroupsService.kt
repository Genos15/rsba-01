package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.dao.GroupDBHandler
import com.rsba.usermicroservice.dao.GroupDBQuery
import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.Group
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.domain.model.WorkingCenter
import com.rsba.usermicroservice.query.database.*
import com.rsba.usermicroservice.repository.GroupsRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import java.util.*
import java.util.stream.Collectors

@Service
class GroupsService(
    private val logger: KLogger,
    private val dbQuery: GroupDBQuery,
    private val dbHandler: GroupDBHandler,
    private val database: DatabaseClient
) : GroupsRepository {

    override suspend fun onCreate(input: CreateGroupInput, token: UUID): Optional<Group> {
        logger.warn { "+GroupsService -> onCreate" }
        return database.sql(GroupsDBQueries.onCreateGroupQueryFrom(input = input))
            .map { row, meta -> GroupsDatabaseHandler.one(row = row, meta = meta) }
            .first()
            .handle { income: Optional<Group>, sink: SynchronousSink<Optional<Group>> ->
                if (income.isPresent) {
                    sink.next(income)
                } else {
                    sink.error(RuntimeException("Что-то пошло не так"))
                }
            }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun createOrEditGroup(input: CreateOrEditGroupInput, token: UUID): Optional<Group> =
        database.sql(dbQuery.onCreateOrEditGroup(input = input, token = token))
            .map { row, meta -> dbHandler.one(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun onAddUserInGroup(
        input: AddUserInGroupInput,
        token: UUID
    ): Optional<Group> {
        logger.warn { "+GroupsService -> onAddUserInGroup" }
        return database.sql(GroupsDBQueries.onAddUserInGroupQueryFrom(input = input))
            .map { row, meta -> GroupsDatabaseHandler.one(row = row, meta = meta) }
            .first()
            .handle { income: Optional<Group>, sink: SynchronousSink<Optional<Group>> ->
                if (income.isPresent) {
                    sink.next(income)
                } else {
                    sink.error(RuntimeException(" Что-то пошло не так"))
                }
            }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun onDeleteUserInGroup(input: DeleteUserInGroupInput, token: UUID): Int {
        logger.warn { "+GroupsService -> onDeleteUserInGroup" }
        return database.sql(GroupsDBQueries.onDeleteUserInGroupQueryFrom(input = input))
            .map { row, meta -> GroupsDatabaseHandler.count(row = row, meta = meta) }
            .first()
            .handle { income: Int, sink: SynchronousSink<Int> ->
                if (income > 0) {
                    sink.next(income)
                } else {
                    sink.error(RuntimeException("Невозможно заблокировать пользователя(ов). Что-то пошло не так"))
                }
            }
            .awaitFirstOrElse { 0 }
    }

    override suspend fun onDeleteGroups(input: List<UUID>, token: UUID): Int {
        logger.warn { "+GroupsService -> onDeleteGroups" }
        return Flux.fromIterable(input)
            .flatMap {
                database.sql(GroupsDBQueries.onDeleteGroupsQueryFrom(input = it))
                    .map { row, meta -> GroupsDatabaseHandler.count(row = row, meta = meta) }
                    .first()
            }
            .collectList()
            .handle { income: List<Int>?, sink: SynchronousSink<Int> ->
                if (income != null && income.isNotEmpty()) {
                    sink.next(income.sum())
                } else {
                    sink.error(RuntimeException("НЕВОЗМОЖНОСТЬ УДАЛИТЬ ПУСТОЙ СПИСОК ГРУПП"))
                }
            }
            .awaitFirstOrElse { 0 }
    }

    override suspend fun onRetrieveAll(
        first: Int,
        after: UUID?,
        token: UUID
    ): MutableList<Group> {
        return database.sql(GroupsDBQueries.onRetrieveAllGroupsQueryFrom(first = first, after = after))
            .map { row, meta -> GroupsDatabaseHandler.readAll(row = row, meta = meta) }
            .first()
            .handle { income: Map<UUID?, Group>, sink: SynchronousSink<Map<UUID?, Group>> ->
                if (income.isNotEmpty()) {
                    sink.next(income)
                } else {
                    sink.error(RuntimeException("Невозможно получить список всех групп."))
                }
            }
            .map { it.values.toMutableList() }
            .awaitFirstOrElse { emptyList<Group>().toMutableList() }
    }

    override suspend fun onAllocateRoleToGroup(input: AllocateRoleToGroup, token: UUID): Int {
        logger.warn { "+GroupsService -> onAllocateRoleToGroup" }
        val forDb = AllocateRoleToGroupForDatabase(income = input)
        return database.sql(GroupsDBQueries.onAllocateRoleToGroupQueryFrom(input = forDb))
            .map { row, meta -> GroupsDatabaseHandler.count(row = row, meta = meta) }
            .first()
            .handle { income: Int, sink: SynchronousSink<Int> ->
                if (income > 0) {
                    sink.next(income)
                } else {
                    sink.error(RuntimeException("Невозможно заблокировать пользователя(ов). Что-то пошло не так"))
                }
            }
            .awaitFirstOrElse { 0 }
    }

    override suspend fun retrieveGroupById(groupId: UUID, token: UUID): Optional<Group> {
        return database.sql(GroupsDBQueries.retrieveGroupById(groupId = groupId, token = token))
            .map { row, meta -> GroupsDatabaseHandler.one(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun retrieveWorkingCenterInGroup(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID?
    ): Map<UUID, List<WorkingCenter>> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(
                GroupsDBQueries.retrieveWorkingCenterByGroupId(
                    groupId = id,
                    token = UUID.randomUUID()
                )
            ).map { row, meta -> WorkingCenterDBHandler.all(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }.collect(Collectors.toList())
        .map {
            val map = mutableMapOf<UUID, List<WorkingCenter>>()
            it.forEach { element -> map[element.key] = element.value }
            return@map map.toMap()
        }.awaitFirstOrElse { emptyMap() }

    override suspend fun retrieveManagersInGroup(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID?
    ): Map<UUID, List<User>> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(
                GroupsDBQueries.retrieveManagersByGroupId(
                    groupId = id,
                    token = UUID.randomUUID()
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

    override suspend fun addManagerInGroup(input: ManagerInGroupInput, token: UUID): Optional<Group> =
        database.sql(GroupsDBQueries.pickManager(input = input, token = token))
            .map { row, meta -> dbHandler.one(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun deleteManagerInGroup(input: ManagerInGroupInput, token: UUID): Optional<Group> =
        database.sql(GroupsDBQueries.unpickManager(input = input, token = token))
            .map { row, meta -> dbHandler.one(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }
}