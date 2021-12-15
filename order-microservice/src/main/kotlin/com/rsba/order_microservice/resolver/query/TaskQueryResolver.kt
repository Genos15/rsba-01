package  com.rsba.order_microservice.resolver.query

import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.TaskRepository
import com.rsba.order_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*


@Component
class TaskQueryResolver(private val service: TaskRepository, private val deduct: TokenAnalyzer) : GraphQLQueryResolver,
    GenericRetrieveConnection {

    suspend fun retrieveTaskDepartments(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Group> = perform(
        entries = service.departments(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun retrieveTaskParameters(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Parameter> = perform(
        entries = service.parameters(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun findTaskOperation(id: UUID, environment: DataFetchingEnvironment): Optional<Operation> = perform(
        entries = service.operation(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun findTaskItem(id: UUID, environment: DataFetchingEnvironment): Optional<Item> = perform(
        entries = service.item(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )
}