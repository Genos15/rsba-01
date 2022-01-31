package com.rsba.tasks_microservice.resolver.query

import com.rsba.tasks_microservice.domain.model.*
import com.rsba.tasks_microservice.domain.repository.CommentRepository
import com.rsba.tasks_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class CommentQueryResolver(val service: CommentRepository, private val deduct: TokenAnalyzer) :
    GraphQLQueryResolver, GenericRetrieveConnection {

    suspend fun findCommentReporter(id: UUID, environment: DataFetchingEnvironment): Optional<User> = perform(
        entries = service.user(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun countComments(
        hostId: UUID,
        layer: CommentLayer? = null,
        environment: DataFetchingEnvironment
    ): Int =
        service.count(token = deduct(environment = environment), layer = layer, hostId = hostId)

}