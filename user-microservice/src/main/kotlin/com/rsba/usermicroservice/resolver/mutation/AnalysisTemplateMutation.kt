package com.rsba.usermicroservice.resolver.mutation

import com.rsba.usermicroservice.context.token.TokenImpl
import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.AnalysisTemplate
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.AnalysisTemplateRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class AnalysisTemplateMutation(
    private val service: AnalysisTemplateRepository,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditAnalysisTemplate(
        input: CreateOrEditAnalysisTemplateInput,
        environment: DataFetchingEnvironment
    ): Optional<AnalysisTemplate> {
        logger.warn { "+AnalysisTemplateMutation -> createOrEditAnalysisTemplate" }
        return service.createOrEdit(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun deleteAnalysisTemplate(input: UUID, environment: DataFetchingEnvironment): Boolean {
        logger.warn { "+AnalysisTemplateMutation -> deleteAnalysisTemplate" }
        return service.delete(input = input, token = tokenImpl.read(environment = environment))
    }

}