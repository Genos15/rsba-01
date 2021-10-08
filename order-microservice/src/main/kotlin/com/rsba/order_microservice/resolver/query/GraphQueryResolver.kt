package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.context.token.TokenImpl
import com.rsba.order_microservice.domain.input.ElkGraphInput
import com.rsba.order_microservice.domain.model.ElkGraphData
import com.rsba.order_microservice.repository.GraphRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment


@Component
class GraphQueryResolver(private val service: GraphRepository, private val tokenImpl: TokenImpl) :
    GraphQLQueryResolver {
    @AdminSecured
    suspend fun constructElkGraph(
        id: ElkGraphInput,
        env: DataFetchingEnvironment
    ): ElkGraphData = service.constructElkGraph(input = id, token = tokenImpl.read(environment = env))
}