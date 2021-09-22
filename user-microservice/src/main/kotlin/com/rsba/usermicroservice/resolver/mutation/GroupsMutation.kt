package com.rsba.usermicroservice.resolver.mutation

import com.rsba.usermicroservice.context.token.TokenImpl
import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.Group
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.GroupsRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class GroupsMutation(
    private val service: GroupsRepository,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLMutationResolver {

    @AdminSecured
    suspend fun createGroup(input: CreateGroupInput, environment: DataFetchingEnvironment): Optional<Group> {
        logger.warn { "+GroupsMutation -> createGroup" }
        return service.onCreate(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun createOrEditGroup(
        input: CreateOrEditGroupInput,
        environment: DataFetchingEnvironment
    ): Optional<Group> {
        logger.warn { "+GroupsMutation -> createOrEditGroup" }
        return service.createOrEditGroup(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun addUserInGroup(input: AddUserInGroupInput, environment: DataFetchingEnvironment): Optional<Group> {
        logger.warn { "+GroupsMutation -> addUserInGroup" }
        return service.onAddUserInGroup(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun deleteUserInGroup(input: DeleteUserInGroupInput, environment: DataFetchingEnvironment): Int {
        logger.warn { "+GroupsMutation -> deleteUserInGroup" }
        return service.onDeleteUserInGroup(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun deleteGroups(input: List<UUID>, environment: DataFetchingEnvironment): Int {
        logger.warn { "+GroupsMutation -> deleteGroups" }
        return service.onDeleteGroups(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun allocateRoleToGroup(input: AllocateRoleToGroup, environment: DataFetchingEnvironment): Int {
        logger.warn { "+GroupsMutation -> allocateRoleToGroup" }
        return service.onAllocateRoleToGroup(input = input, token = tokenImpl.read(environment = environment))
    }


    @AdminSecured
    suspend fun pickManagerInGroup(
        input: ManagerInGroupInput,
        environment: DataFetchingEnvironment
    ): Optional<Group> {
        logger.warn { "+GroupsMutation -> pickManagerInGroup" }
        return service.addManagerInGroup(input = input, token = tokenImpl.read(environment = environment))
    }


    @AdminSecured
    suspend fun unpickManagerInGroup(
        input: ManagerInGroupInput,
        environment: DataFetchingEnvironment
    ): Optional<Group> {
        logger.warn { "+GroupsMutation -> unpickManagerInGroup" }
        return service.deleteManagerInGroup(input = input, token = tokenImpl.read(environment = environment))
    }

}