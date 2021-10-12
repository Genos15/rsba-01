package com.rsba.usermicroservice.resolver.mutation

import com.rsba.usermicroservice.context.token.ITokenImpl
import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.WorkingCenter
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.WorkingCenterRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class WorkingCenterMutation(
    private val service: WorkingCenterRepository,
    private val logger: KLogger,
) : GraphQLMutationResolver, ITokenImpl {

    @AdminSecured
    suspend fun createOrEditWorkingCenter(
        input: CreateOrEditWorkingCenterInput,
        environment: DataFetchingEnvironment
    ): Optional<WorkingCenter> {
        logger.warn { "+WorkingCenterMutation -> createOrEditWorkingCenter" }
        return service.createOrEdit(input = input, token = readToken(environment = environment))
    }

    @AdminSecured
    suspend fun deleteWorkingCenter(input: UUID, environment: DataFetchingEnvironment): Boolean {
        logger.warn { "+WorkingCenterMutation -> deleteWorkingCenter" }
        return service.delete(input = input, token = readToken(environment = environment))
    }

    @AdminSecured
    suspend fun addUserInWorkingCenter(
        input: UserInWorkingCenterInput,
        environment: DataFetchingEnvironment
    ): Optional<WorkingCenter> {
        logger.warn { "+WorkingCenterMutation -> addUserInWorkingCenter" }
        return service.addUserInWorkingCenter(input = input, token = readToken(environment = environment))
    }

    @AdminSecured
    suspend fun deleteUserInWorkingCenter(
        input: UserInWorkingCenterInput,
        environment: DataFetchingEnvironment
    ): Optional<WorkingCenter> {
        logger.warn { "+WorkingCenterMutation -> deleteUserInWorkingCenter" }
        return service.deleteUserInWorkingCenter(input = input, token = readToken(environment = environment))
    }

    @AdminSecured
    suspend fun pickManagerInWorkingCenter(
        input: ManagerInWorkingCenterInput,
        environment: DataFetchingEnvironment
    ): Optional<WorkingCenter> {
        logger.warn { "+WorkingCenterMutation -> pickManagerInWorkingCenter" }
        return service.addManagerInWorkingCenter(input = input, token = readToken(environment = environment))
    }


    @AdminSecured
    suspend fun unpickManagerInWorkingCenter(
        input: ManagerInWorkingCenterInput,
        environment: DataFetchingEnvironment
    ): Optional<WorkingCenter> {
        logger.warn { "+WorkingCenterMutation -> unpickManagerInWorkingCenter" }
        return service.deleteManagerInWorkingCenter(input = input, token = readToken(environment = environment))
    }

}