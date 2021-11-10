package  com.rsba.order_microservice.resolver.mutation

import   com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.data.context.token.TokenImpl
import com.rsba.order_microservice.domain.model.Comment
import com.rsba.order_microservice.domain.input.CommentTaskInput
import com.rsba.order_microservice.domain.repository.CommentRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class CommentMutation(
    private val service: CommentRepository,
    private val tokenImpl: TokenImpl,
    private val logger: KLogger
) :
    GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditCommentOfTask(
        input: CommentTaskInput,
        environment: DataFetchingEnvironment
    ): Optional<Comment> {
        logger.warn { "+CommentMutation -> createOrEditCommentOfTask" }
        return service.createOrEditComment(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun deleteComment(
        input: UUID,
        environment: DataFetchingEnvironment
    ): Boolean {
        logger.warn { "+CommentMutation -> deleteComment" }
        return service.deleteComment(input = input, token = tokenImpl.read(environment = environment))
    }

}