package com.rsba.usermicroservice.resolver.mutation

import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.Role
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.RolesRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class RolesMutation(private val service: RolesRepository, private val logger: KLogger) : GraphQLMutationResolver {

    @AdminSecured
    suspend fun createRole(input: CreateRoleInput, environment: DataFetchingEnvironment): Optional<Role> {
        logger.warn { "+-- RolesMutation -> createRole" }
        return service.onCreate(input = input, environment = environment)
    }

    @AdminSecured
    suspend fun editRoleOfUser(input: EditRoleOfUserInput, environment: DataFetchingEnvironment): Optional<Role> {
        logger.warn { "+-- RolesMutation -> editRoleOfUser" }
        return service.onEditRoleOfUser(input = input, environment = environment)
    }

    @AdminSecured
    suspend fun addPermissionInRole(
        input: PermissionInRoleInput,
        environment: DataFetchingEnvironment
    ): Optional<Role> {
        logger.warn { "+-- RolesMutation -> addPermissionInRole" }
        return service.onAddPermissionInRole(input = input, environment = environment)
    }

    @AdminSecured
    suspend fun editPermissionInRole(
        input: PermissionInRoleInput,
        environment: DataFetchingEnvironment
    ): Optional<Role> {
        logger.warn { "+-- RolesMutation -> editPermissionInRole" }
        return service.onEditPermissionInRole(input = input, environment = environment)
    }

    @AdminSecured
    suspend fun deletePermissionInRole(input: DeletePermissionInRoleInput, environment: DataFetchingEnvironment): Int {
        logger.warn { "+-- RolesMutation -> deletePermissionInRole" }
        return service.onDeletePermissionInRole(input = input, environment = environment)
    }

    @AdminSecured
    suspend fun deleteRoles(input: List<UUID>, environment: DataFetchingEnvironment): Int {
        logger.warn { "+-- GroupsMutation -> deleteRoles" }
        return service.onDeleteRoles(input = input, environment = environment)
    }

}