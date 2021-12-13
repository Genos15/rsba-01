package  com.rsba.order_microservice.resolver.query

import com.rsba.order_microservice.data.context.token.TokenImpl
import com.rsba.order_microservice.domain.repository.GraphRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component


@Component
class GraphQueryResolver(private val service: GraphRepository, private val tokenImpl: TokenImpl) :
    GraphQLQueryResolver {
//    @AdminSecured
//    suspend fun constructElkGraph(
//        id: ElkGraphInput,
//        env: DataFetchingEnvironment
//    ): ElkGraphData = service.constructElkGraph(input = id, token = tokenImpl.read(environment = env))
}