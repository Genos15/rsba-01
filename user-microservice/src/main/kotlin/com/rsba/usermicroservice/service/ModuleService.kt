package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.Module
import com.rsba.usermicroservice.query.database.ModuleDatabaseHandler
import com.rsba.usermicroservice.query.database.ModuleDatabaseQueries
import com.rsba.usermicroservice.repository.ModuleRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class ModuleService(
    private val logger: KLogger,
    private val database: DatabaseClient,
) : ModuleRepository {

    override suspend fun createModule(input: CreateModuleInput): Optional<Module> {
        logger.warn { "+-- ModuleService -> createModule" }
        logger.warn { input.toJson() }
        return database.sql(ModuleDatabaseQueries.onCreateModuleQueryFrom(input = input))
            .map { row, meta -> ModuleDatabaseHandler.readAll(row = row, meta = meta) }
            .first()
            .map { Optional.ofNullable(it.values.first()) }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun editModule(input: EditModuleInput): Optional<Module> {
        logger.warn { "+-- ModuleService -> editModule" }
        logger.warn { input.toJson() }
        return database.sql(ModuleDatabaseQueries.onEditModuleQueryFrom(input = input))
            .map { row, meta -> ModuleDatabaseHandler.readAll(row = row, meta = meta) }
            .first()
            .map { Optional.ofNullable(it.values.first()) }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun addDependencyInModule(input: AddDependencyInModuleInput): Optional<Module> {
        logger.warn { "+-- ModuleService -> addDependencyInModule" }
        logger.warn { input.toJson() }
        return database.sql(ModuleDatabaseQueries.onAddDependencyInModuleQueryFrom(input = input))
            .map { row, meta -> ModuleDatabaseHandler.readAll(row = row, meta = meta) }
            .first()
            .map { Optional.ofNullable(it.values.first()) }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun addPermissionInModule(input: AddPermissionInModuleInput): Optional<Module> {
        logger.warn { "+-- ModuleService -> addPermissionInModule" }
        logger.warn { input.toJson() }
        return database.sql(ModuleDatabaseQueries.onAddPermissionInModuleQueryFrom(input = input))
            .map { row, meta -> ModuleDatabaseHandler.readAll(row = row, meta = meta) }
            .first()
            .map { Optional.ofNullable(it.values.first()) }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun deletePermissionInModule(input: DeletePermissionInModuleInput): Int {
        logger.warn { "+-- ModuleService -> deletePermissionInModule" }
        logger.warn { input.toJson() }
        return database.sql(ModuleDatabaseQueries.onDeletePermissionInModuleQueryFrom(input = input))
            .map { row, meta -> ModuleDatabaseHandler.count(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { 0 }
    }

    override suspend fun retrieveAllModule(first: Int, after: UUID?): List<Module> {
        logger.warn { "+-- ModuleService -> retrieveAllModule" }
        return database.sql(ModuleDatabaseQueries.onRetrieveModuleQueryFrom())
            .map { row, meta -> ModuleDatabaseHandler.readAll(row = row, meta = meta) }
            .first()
            .map { it.values.toList() }
            .awaitFirstOrElse { emptyList() }
    }

}