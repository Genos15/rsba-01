package  com.rsba.order_microservice.resolver.mutation

import com.rsba.order_microservice.data.context.token.TokenImpl
import com.rsba.order_microservice.domain.repository.ItemRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component

@Component
class ItemMutation(private val service: ItemRepository, private val tokenImpl: TokenImpl) : GraphQLMutationResolver {
//
//    @AdminSecured
//    suspend fun addOrEditComponentInItem(
//        input: ItemAndItemInput,
//        environment: DataFetchingEnvironment
//    ): Optional<Item> =
//        service.addOrEditComponentInItem(input = input, token = tokenImpl.read(environment = environment))
//
//    @AdminSecured
//    suspend fun removeComponentInItem(
//        input: ItemAndItemInput,
//        environment: DataFetchingEnvironment
//    ): Optional<Item> = service.removeComponentInItem(input = input, token = tokenImpl.read(environment = environment))

}