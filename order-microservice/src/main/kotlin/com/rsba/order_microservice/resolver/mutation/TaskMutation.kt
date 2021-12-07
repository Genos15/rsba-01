package  com.rsba.order_microservice.resolver.mutation

import   com.rsba.order_microservice.domain.input.*
import   com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.data.context.token.TokenImpl
import com.rsba.order_microservice.domain.model.Order
import com.rsba.order_microservice.domain.model.Task
import com.rsba.order_microservice.domain.repository.TaskRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskMutation(
    private val service: TaskRepository,
    private val tokenImpl: TokenImpl,
    private val logger: KLogger
) :
    GraphQLMutationResolver {

    @AdminSecured
    suspend fun pinDepartmentsInTask(
        input: DepartmentWithTask,
        environment: DataFetchingEnvironment
    ): Optional<Task> {
        logger.warn { "+TaskMutation -> pinDepartmentsInTask" }
        return service.pinDepartmentsInTask(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun pinWorkingCentersInTask(
        input: WorkingCenterWithTask,
        environment: DataFetchingEnvironment
    ): Optional<Task> {
        logger.warn { "+TaskMutation -> pinWorkingCentersInTask" }
        return service.pinWorkingCentersInTask(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun pinUsersInTask(
        input: UserWithTask,
        environment: DataFetchingEnvironment
    ): Optional<Task> {
        logger.warn { "+TaskMutation -> pinWorkingUsersInTask" }
        return service.pinUsersInTask(input = input, token = tokenImpl.read(environment = environment))
    }


    @AdminSecured
    suspend fun unpinDepartmentsInTask(
        input: DepartmentWithTask,
        environment: DataFetchingEnvironment
    ): Optional<Task> {
        logger.warn { "+TaskMutation -> unpinDepartmentsInTask" }
        return service.unpinDepartmentsInTask(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun unpinWorkingCentersInTask(
        input: WorkingCenterWithTask,
        environment: DataFetchingEnvironment
    ): Optional<Task> {
        logger.warn { "+TaskMutation -> unpinWorkingCentersInTask" }
        return service.unpinWorkingCentersInTask(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun unpinUsersInTask(input: UserWithTask, environment: DataFetchingEnvironment): Optional<Task> {
        logger.warn { "+TaskMutation -> unpinWorkingUsersInTask" }
        return service.unpinUsersInTask(input = input, token = tokenImpl.read(environment = environment))
    }

//    @AdminSecured
//    suspend fun editTask(input: TaskInput, environment: DataFetchingEnvironment): Optional<Task> {
//        logger.warn { "+TaskMutation -> editTask" }
//        return service.editTask(input = input, token = tokenImpl.read(environment = environment))
//    }

    @AdminSecured
    suspend fun terminateTask(input: UUID, environment: DataFetchingEnvironment): Optional<Task> {
        logger.warn { "+TaskMutation -> terminateTask" }
        return service.terminateTask(id = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun createOrEditTask(input: TaskInput, environment: DataFetchingEnvironment): Optional<Task> {
        logger.warn { "+TaskMutation -> createOrEditTask" }
        return service.createOrEdit(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun deleteTask(input: TaskInput, environment: DataFetchingEnvironment): Optional<Order> {
        logger.warn { "+TaskMutation -> deleteTask" }
        return service.delete(input = input, token = tokenImpl.read(environment = environment))
    }

}