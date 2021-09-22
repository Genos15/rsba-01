package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.domain.input.ModuleWithPermission
import com.rsba.usermicroservice.domain.input.PermissionOfModule
import com.rsba.usermicroservice.domain.model.Permission
import com.rsba.usermicroservice.query.database.PermissionDatabaseHandler
import com.rsba.usermicroservice.query.database.PermissionDatabaseQueries
import com.rsba.usermicroservice.repository.PermissionRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.stream.Collectors

@Service
class PermissionService(
    private val logger: KLogger,
    private val database: DatabaseClient,
) : PermissionRepository {

    override suspend fun onRetrieveByModuleId(
        moduleIds: Set<UUID>,
        moduleId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Permission>> {
        logger.warn { "+------ PermissionService/onRetrieveByModuleId" }
        return Flux.fromIterable(moduleIds)
            .flatMap { id ->
                return@flatMap database.sql(PermissionDatabaseQueries.retrieveByModuleId(moduleId = id))
                    .map { row, meta -> PermissionDatabaseHandler.readAll(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it.values.toList()) }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<UUID, List<Permission>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { emptyMap() }
    }

    override suspend fun onRetrieveByRoleId(
        roleIds: Set<UUID>,
        moduleId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Permission>> {
        logger.warn { "+------ PermissionService/onRetrieveByRoleId" }
        return Flux.fromIterable(roleIds)
            .flatMap { id ->
                return@flatMap database.sql(PermissionDatabaseQueries.retrieveByRoleId(roleId = id))
                    .map { row, meta -> PermissionDatabaseHandler.readAll(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it.values.toList()) }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<UUID, List<Permission>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { emptyMap() }
    }

    override suspend fun onRetrievePermissionsOfModuleInRole(
        modulesInstances: Set<ModuleWithPermission>,
        moduleId: UUID,
        page: Int,
        size: Int
    ): Map<ModuleWithPermission, MutableList<PermissionOfModule>> {
        logger.warn { "+--- PermissionService -> onRetrievePermissionsOfModuleInRole" }
        return Flux.fromIterable(modulesInstances)
            .flatMap { ins ->
                return@flatMap database.sql(PermissionDatabaseQueries.onRetrievePermissionState(instance = ins))
                    .map { row, meta -> PermissionDatabaseHandler.list(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(ins, it) }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<ModuleWithPermission, MutableList<PermissionOfModule>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { emptyMap() }
    }

}