package com.rsba.usermicroservice.resolver.mutation

import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.Module
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.ModuleRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class ModuleMutation(private val service: ModuleRepository, private val logger: KLogger) : GraphQLMutationResolver {

    @AdminSecured
    suspend fun createModule(input: CreateModuleInput, environment: DataFetchingEnvironment): Optional<Module> {
        logger.warn { "+-- ModuleMutation -> createModule" }
        return service.createModule(input = input)
    }

    @AdminSecured
    suspend fun editModule(input: EditModuleInput, environment: DataFetchingEnvironment): Optional<Module> {
        logger.warn { "+-- ModuleMutation -> editModule" }
        return service.editModule(input = input)
    }

    @AdminSecured
    suspend fun addDependencyInModule(input: AddDependencyInModuleInput, environment: DataFetchingEnvironment): Optional<Module> {
        logger.warn { "+-- ModuleMutation -> addDependencyInModule" }
        return service.addDependencyInModule(input = input)
    }

    @AdminSecured
    suspend fun addPermissionInModule(input: AddPermissionInModuleInput, environment: DataFetchingEnvironment): Optional<Module> {
        logger.warn { "+-- ModuleMutation -> addPermissionInModule" }
        return service.addPermissionInModule(input = input)
    }

    @AdminSecured
    suspend fun deletePermissionInModule(input: DeletePermissionInModuleInput, environment: DataFetchingEnvironment): Int {
        logger.warn { "+-- ModuleMutation -> deletePermissionInModule" }
        return service.deletePermissionInModule(input = input)
    }

}