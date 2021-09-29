package com.rsba.usermicroservice.resolver.mutation


import com.rsba.usermicroservice.context.token.ITokenImpl
import com.rsba.usermicroservice.domain.input.ExecutorInput
import com.rsba.usermicroservice.domain.model.Executor
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.ExecutorRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class ExecutorMutation(
    private val service: ExecutorRepository,
    private val logger: KLogger,
) : GraphQLMutationResolver, ITokenImpl {

    @AdminSecured
    suspend fun createOrEditExecutor(
        input: ExecutorInput,
        environment: DataFetchingEnvironment
    ): Optional<Executor> {
        logger.warn { "+ExecutorMutation->createOrEdit" }
        return service.createOrEdit(input = input, token = readToken(environment = environment))
    }

    @AdminSecured
    suspend fun renewExecutorPin(
        input: ExecutorInput,
        environment: DataFetchingEnvironment
    ): Optional<Executor> {
        logger.warn { "+ExecutorMutation->renewExecutorPin" }
        return service.renewPin(input = input, token = readToken(environment = environment))
    }

    @AdminSecured
    suspend fun deleteExecutor(input: UUID, environment: DataFetchingEnvironment): Boolean {
        logger.warn { "+ExecutorMutation->delete" }
        return service.delete(input = input, token = readToken(environment = environment))
    }
}