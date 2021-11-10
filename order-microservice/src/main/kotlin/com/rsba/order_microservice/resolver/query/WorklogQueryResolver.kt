package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.configuration.request_helper.CursorUtil
import com.rsba.order_microservice.data.context.token.TokenImpl
import com.rsba.order_microservice.domain.model.Worklog
import com.rsba.order_microservice.domain.repository.WorklogRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import java.util.*


@Component
class WorklogQueryResolver(
    private val service: WorklogRepository,
    private val logger: KLogger,
    private val cursorUtil: CursorUtil,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveWorklogsByTaskId(
        taskId: UUID,
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Worklog>? {
        logger.warn { "+WorklogQueryResolver -> retrieveWorklogsByTaskId" }
        val edges: List<Edge<Worklog>> =
            service.retrieveWorklogsByTaskId(
                token = tokenImpl.read(environment = env),
                first = first,
                after = after,
                taskId = taskId
            ).map {
                return@map DefaultEdge(it, cursorUtil.createCursorWith(it.id))
            }.take(first)

        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )

        return DefaultConnection(edges, pageInfo)
    }


}