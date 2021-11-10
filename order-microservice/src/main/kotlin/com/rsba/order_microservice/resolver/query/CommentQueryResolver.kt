package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.configuration.request_helper.CursorUtil
import com.rsba.order_microservice.data.context.token.TokenImpl
import com.rsba.order_microservice.domain.model.Comment
import com.rsba.order_microservice.domain.repository.CommentRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import java.util.*


@Component
class CommentQueryResolver(
    private val service: CommentRepository,
    private val logger: KLogger,
    private val cursorUtil: CursorUtil,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveCommentsByTaskId(
        taskId: UUID,
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Comment>? {
        logger.warn { "+CommentQueryResolver -> retrieveCommentsByTaskId" }
        val edges: List<Edge<Comment>> =
            service.retrieveCommentsByTaskId(
                taskId = taskId,
                token = tokenImpl.read(environment = env),
                first = first,
                after = after
            ).map { DefaultEdge(it, cursorUtil.createCursorWith(it.id)) }.take(first)

        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )

        return DefaultConnection(edges, pageInfo)
    }
}