package com.rsba.usermicroservice.repository

import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.Module
import com.rsba.usermicroservice.domain.model.Role
import java.util.*
import kotlin.jvm.Throws

interface ModuleRepository {
    @Throws(RuntimeException::class)
    suspend fun createModule(input: CreateModuleInput): Optional<Module>

    @Throws(RuntimeException::class)
    suspend fun editModule(input: EditModuleInput): Optional<Module>

    @Throws(RuntimeException::class)
    suspend fun addDependencyInModule(input: AddDependencyInModuleInput): Optional<Module>

    @Throws(RuntimeException::class)
    suspend fun addPermissionInModule(input: AddPermissionInModuleInput): Optional<Module>

    @Throws(RuntimeException::class)
    suspend fun deletePermissionInModule(input: DeletePermissionInModuleInput): Int

    @Throws(RuntimeException::class)
    suspend fun retrieveAllModule(first: Int, after: UUID?): List<Module>
}