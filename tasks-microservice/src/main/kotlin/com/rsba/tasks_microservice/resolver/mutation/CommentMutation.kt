package com.rsba.tasks_microservice.resolver.mutation

import  com.rsba.tasks_microservice.domain.input.*
import com.rsba.tasks_microservice.domain.model.Comment
import com.rsba.tasks_microservice.domain.model.CommentLayer
import com.rsba.tasks_microservice.domain.repository.CommentRepository
import com.rsba.tasks_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class CommentMutation(private val service: CommentRepository, private val deduct: TokenAnalyzer) :
    GraphQLMutationResolver {

    suspend fun createOrEditComment(
        input: CommentInput,
        layer: CommentLayer? = null,
        environment: DataFetchingEnvironment
    ): Optional<Comment> =
        service.toCreateOrEdit(input = input, token = deduct(environment = environment), layer = layer)

    suspend fun deleteComment(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toDelete(input = input, token = deduct(environment = environment))

}