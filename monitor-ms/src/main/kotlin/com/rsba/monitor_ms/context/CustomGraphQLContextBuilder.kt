package  com.rsba.monitor_ms.context

import   com.rsba.monitor_ms.context.dataloader.DataLoaderRegistryFactory
import graphql.kickstart.execution.context.GraphQLContext
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.websocket.Session
import javax.websocket.server.HandshakeRequest

@Component
class CustomGraphQLContextBuilder(private val registry: DataLoaderRegistryFactory, private val logger: KLogger) :
    GraphQLServletContextBuilder {

    override fun build(request: HttpServletRequest?, response: HttpServletResponse?): GraphQLContext {
        logger.warn { "+------ CustomGraphQLContextBuilder/build…" }
        val userId = request?.getHeader("user_id")
        val ctx = DefaultGraphQLServletContext.createServletContext()
            .with(request)
            .with(response)
            .with(registry.create(instanceId = UUID.randomUUID()))
            .build()

        return CustomGraphQLContext(user_id = userId, context = ctx)
    }

    override fun build(p0: Session?, p1: HandshakeRequest?): GraphQLContext {
        logger.warn { "+------ we should implement the second one…, open = ${p0?.isOpen}" }
        return DefaultGraphQLWebSocketContext.createWebSocketContext()
            .with(p0)
            .with(p1)
            .build()
    }

    override fun build(): GraphQLContext {
        logger.warn { "+------ we should implement the third one…" }
        TODO("Not yet implemented")
    }
}