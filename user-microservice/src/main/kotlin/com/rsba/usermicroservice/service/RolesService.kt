package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.context.token.TokenImpl
import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.Role
import com.rsba.usermicroservice.query.database.RolesDatabaseHandler
import com.rsba.usermicroservice.query.database.RolesDatabaseQueries
import com.rsba.usermicroservice.repository.RolesRepository
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import java.util.*
import java.util.stream.Collectors

@Service
class RolesService(
    private val logger: KLogger,
    private val database: DatabaseClient,
    private val tokenImpl: TokenImpl
) : RolesRepository {
    override suspend fun onCreate(input: CreateRoleInput, environment: DataFetchingEnvironment): Optional<Role> {
        logger.warn { "+------ RolesService/onCreate" }
        return database.sql(
            RolesDatabaseQueries.onCreateRoleQueryFrom(
                input = input,
                token = tokenImpl.read(environment = environment)
            )
        )
            .map { row, meta -> RolesDatabaseHandler.one(row = row, meta = meta) }
            .first()
            .handle { income: Optional<Role>, sink: SynchronousSink<Optional<Role>> ->
                if (income.isPresent) {
                    sink.next(income)
                } else {
                    sink.error(RuntimeException("Что-то пошло не так"))
                }
            }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun onEditRoleOfUser(
        input: EditRoleOfUserInput,
        environment: DataFetchingEnvironment
    ): Optional<Role> {
        logger.warn { "+------ RolesService/onEditRoleOfUser" }
        return database.sql(
            RolesDatabaseQueries.onEditRoleOfUserQueryFrom(
                input = input,
                token = tokenImpl.read(environment = environment)
            )
        )
            .map { row, meta -> RolesDatabaseHandler.one(row = row, meta = meta) }
            .first()
            .handle { income: Optional<Role>, sink: SynchronousSink<Optional<Role>> ->
                if (income.isPresent) {
                    sink.next(income)
                } else {
                    sink.error(RuntimeException("Что-то пошло не так"))
                }
            }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun onAddPermissionInRole(
        input: PermissionInRoleInput,
        environment: DataFetchingEnvironment
    ): Optional<Role> {
        logger.warn { "+------ RolesService/onAddPermissionInRole" }
        return database.sql(
            RolesDatabaseQueries.onAddPermissionInRoleQueryFrom(
                input = input,
                token = tokenImpl.read(environment = environment)
            )
        )
            .map { row, meta -> RolesDatabaseHandler.one(row = row, meta = meta) }
            .first()
            .handle { income: Optional<Role>, sink: SynchronousSink<Optional<Role>> ->
                if (income.isPresent) {
                    sink.next(income)
                } else {
                    sink.error(RuntimeException("Что-то пошло не так"))
                }
            }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun onEditPermissionInRole(
        input: PermissionInRoleInput,
        environment: DataFetchingEnvironment
    ): Optional<Role> {
        logger.warn { "+------ RolesService/onEditPermissionInRole" }
        return database.sql(RolesDatabaseQueries.onEditPermissionInRoleQueryFrom(input = input))
            .map { row, meta -> RolesDatabaseHandler.one(row = row, meta = meta) }
            .first()
            .handle { income: Optional<Role>, sink: SynchronousSink<Optional<Role>> ->
                if (income.isPresent) {
                    sink.next(income)
                } else {
                    sink.error(RuntimeException("Что-то пошло не так"))
                }
            }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun onDeletePermissionInRole(
        input: DeletePermissionInRoleInput,
        environment: DataFetchingEnvironment
    ): Int {
        logger.warn { "+------ RolesService/onDeletePermissionInRole" }
        return database.sql(RolesDatabaseQueries.onDeletePermissionInRoleQueryFrom(input = input))
            .map { row, meta -> RolesDatabaseHandler.count(row = row, meta = meta) }
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

    override suspend fun onDeleteRoles(input: List<UUID>, environment: DataFetchingEnvironment): Int {
        logger.warn { "+------ RolesService/onDeleteRoles" }
        return database.sql(RolesDatabaseQueries.onDeleteRolesQueryFrom(input = input))
            .map { row, meta -> RolesDatabaseHandler.count(row = row, meta = meta) }
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

    override suspend fun onRetrieveAll(
        first: Int,
        after: UUID?,
        environment: DataFetchingEnvironment
    ): MutableList<Role> {
        return database.sql(
            RolesDatabaseQueries.onRetrieveAllRolesQueryFrom(
                token = tokenImpl.read(environment = environment),
                first = first,
                after = after
            )
        ).map { row, meta -> RolesDatabaseHandler.readAll(row = row, meta = meta) }
            .first()
            .handle { income: Map<UUID?, Role>, sink: SynchronousSink<Map<UUID?, Role>> ->
                if (income.isNotEmpty()) {
                    sink.next(income)
                } else {
                    sink.error(RuntimeException("Невозможно получить список всех ролей."))
                }
            }
            .map { it.values.toMutableList() }
            .awaitFirstOrElse { emptyList<Role>().toMutableList() }
    }

    override suspend fun onRetrieveByUserId(
        userIds: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID?
    ): Map<UUID, Role?> {
        logger.warn { "+--- RolesService -> onRetrieveByUserId" }
        return Flux.fromIterable(userIds)
            .flatMap { id ->
                return@flatMap database.sql(
                    RolesDatabaseQueries.onRetrieveByUserId(userId = id)
                ).map { row, meta -> RolesDatabaseHandler.one(row = row, meta = meta) }
                    .first()
                    .handle { single: Optional<Role>, sink: SynchronousSink<Role> ->
                        if (single.isPresent) {
                            sink.next(single.get())
                        } else {
                            sink.error(RuntimeException("ROLE NOT FOUND"))
                        }
                    }
                    .map { AbstractMap.SimpleEntry<UUID, Role?>(id, it) }
                    .onErrorResume {
                        logger.warn { "error1 = ${it.message}" }
                        Mono.empty()
                    }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<UUID, Role?>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "error2 = ${it.message}" }
                Mono.empty()
            }
            .awaitFirstOrElse { emptyMap() }
    }

    override suspend fun onRetrieveModuleById(
        roleId: UUID,
        first: Int,
        after: UUID?,
        environment: DataFetchingEnvironment
    ): MutableList<ModuleWithPermission> {
        return database.sql(
            RolesDatabaseQueries.onRetrieveModuleByRoleId(
                roleId = roleId,
                token = tokenImpl.read(environment = environment),
                first = first,
                after = after
            )
        ).map { row, meta -> RolesDatabaseHandler.list(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "error = ${it.message}" }
                Mono.empty()
            }
            .awaitFirstOrElse { mutableListOf() }
    }

}