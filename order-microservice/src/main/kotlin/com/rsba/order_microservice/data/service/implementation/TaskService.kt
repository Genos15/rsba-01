package  com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.database.*
import com.rsba.order_microservice.domain.input.*
import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.TaskRepository
import com.rsba.order_microservice.data.service.implementation.tasks.RetrieveTaskImpl
import com.rsba.order_microservice.domain.usecase.common.RetrieveParametersUseCase
import com.rsba.order_microservice.domain.usecase.custom.task.FindItemUseCase
import com.rsba.order_microservice.domain.usecase.custom.task.FindOperationUseCase
import com.rsba.order_microservice.domain.usecase.custom.task.RetrieveDepartmentsUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class TaskService(
    private val logger: KLogger,
    private val database: DatabaseClient,
    @Qualifier("find_task_item") private val findItemUseCase: FindItemUseCase,
    private val findOperationUseCase: FindOperationUseCase,
    private val retrieveDepartmentsUseCase: RetrieveDepartmentsUseCase,
    @Qualifier("retrieve_task_parameters") private val retrieveParametersUseCase: RetrieveParametersUseCase,
) : TaskRepository,
    RetrieveTaskImpl {

    override suspend fun myPersonalInfo(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID?
    ): Map<UUID, PersonalInfo?> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(UserDBQueries.myPersonalInfo(input = id, token = UUID.randomUUID()))
                .map { row -> PersonalInfoDBHandler.one(row = row) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }
        .collectList()
        .map {
            val map = mutableMapOf<UUID, PersonalInfo?>()
            it.forEach { element -> map[element.key] = element.value.orElse(null) }
            return@map map.toMap()
        }
        .onErrorResume {
            logger.warn { "+TaskService -> myPersonalInfo -> error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyMap() }

    override suspend fun myContactInfo(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID?
    ): Map<UUID, List<ContactInfo>> =
        Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(
                    UserDBQueries.myContacts(input = id, token = UUID.randomUUID())
                ).map { row -> ContactInfoDBHandler.all(row = row) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collectList()
            .map {
                val map = mutableMapOf<UUID, List<ContactInfo>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> myContactInfo -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun myUsers(
        ids: Set<WorkingCenter>,
        userId: UUID,
        first: Int,
        after: UUID?
    ): Map<WorkingCenter, List<User>> =
        Flux.fromIterable(ids)
            .flatMap { workcenter ->
                return@flatMap database.sql(
                    TaskDBQueries.myUsers(
                        taskId = workcenter.taskId,
                        workcenterId = workcenter.id
                    )
                ).map { row, meta -> UserDBHandler.all(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(workcenter, it) }
            }
            .collectList()
            .map {
                val map = mutableMapOf<WorkingCenter, List<User>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> myUsers -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun usersInTask(ids: Set<UUID>, userId: UUID, first: Int, after: UUID?): Map<UUID, List<User>> =
        Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(
                    TaskDBQueries.userOfTask(taskId = id)
                ).map { row, meta -> UserDBHandler.all(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collectList()
            .map {
                val map = mutableMapOf<UUID, List<User>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> usersInTask -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun myWorkingCenters(
        ids: Set<Department>,
        userId: UUID,
        first: Int,
        after: UUID?
    ): Map<Department, List<WorkingCenter>> =
        Flux.fromIterable(ids)
            .flatMap { department ->
                return@flatMap database.sql(
                    TaskDBQueries.myWorkingCenters(
                        taskId = department.taskId,
                        groupId = department.id
                    )
                ).map { row, meta -> WorkingCenterDBHandler.all(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(department, it) }
            }
            .collectList()
            .map {
                val map = mutableMapOf<Department, List<WorkingCenter>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> myWorkingCenters -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun myDepartments(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID?
    ): Map<UUID, List<Department>> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(TaskDBQueries.myDepartments(taskId = id))
                .map { row, meta -> DepartmentDBHandler.all(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }
        .collectList()
        .map {
            val map = mutableMapOf<UUID, List<Department>>()
            it.forEach { element -> map[element.key] = element.value }
            return@map map.toMap()
        }
        .onErrorResume {
            logger.warn { "+TaskService -> myComments -> error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyMap() }

    private fun innerItem(ids: Set<UUID>, userId: UUID, first: Int, after: UUID?): Mono<Map<UUID, Item?>> =
        Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(TaskDBQueries.myItem(taskId = id))
                    .map { row -> ItemDBHandler.one(row = row) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collectList()
            .map {
                val map = mutableMapOf<UUID, Item?>()
                it.forEach { element -> map[element.key] = element.value.orElse(null) }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> myOrder -> error = ${it.message}" }
                throw it
            }

    override suspend fun myItem(ids: Set<UUID>, userId: UUID, first: Int, after: UUID?): Map<UUID, Item?> =
        innerItem(ids = ids, userId = userId, first = first, after = after)
            .awaitFirstOrElse { emptyMap() }

    override suspend fun myOrder(ids: Set<UUID>, userId: UUID, first: Int, after: UUID?): Map<UUID, Order?> =
        innerOrder(ids = ids)
            .awaitFirstOrElse { emptyMap() }

    override suspend fun myOperation(ids: Set<UUID>, userId: UUID, first: Int, after: UUID?): Map<UUID, Operation?> =
        innerOperation(ids = ids)
            .awaitFirstOrElse { emptyMap() }

    private fun innerOperation(ids: Set<UUID>): Mono<Map<UUID, Operation?>> =
        Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(TaskDBQueries.myOperation(taskId = id))
                    .map { row, meta -> OperationDBHandler.one(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collectList()
            .map {
                val map = mutableMapOf<UUID, Operation?>()
                it.forEach { element -> map[element.key] = element.value.orElse(null) }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> myOperation -> error = ${it.message}" }
                throw it
            }

    private fun innerOrder(ids: Set<UUID>): Mono<Map<UUID, Order?>> =
        Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(TaskDBQueries.myOrder(taskId = id))
                    .map { row, meta -> OrderDBHandler.one(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collectList()
            .map {
                val map = mutableMapOf<UUID, Order?>()
                it.forEach { element -> map[element.key] = element.value.orElse(null) }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> myOrder -> error = ${it.message}" }
                throw it
            }

    override suspend fun myComments(ids: Set<UUID>, userId: UUID, first: Int, after: UUID?): Map<UUID, List<Comment>> =
        Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(TaskDBQueries.myComments(taskId = id))
                    .map { row, meta -> CommentDBHandler.all(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collectList()
            .map {
                val map = mutableMapOf<UUID, List<Comment>>()
                it.forEach { element -> map[element.key] = element.value ?: emptyList() }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> myComments -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun pinDepartmentsInTask(input: DepartmentWithTask, token: UUID): Optional<Task> =
        Flux.fromIterable(input.departmentIds ?: emptyList())
            .flatMap { id ->
                val entrant = DepartmentWithTaskInput(taskId = input.taskId, departmentId = UUID.fromString(id))
                return@flatMap database.sql(TaskDBQueries.pinDepartments(input = entrant, token = token))
                    .map { row, meta -> TaskDBHandler.one(row = row, meta = meta) }
                    .first()
            }
            .collectList()
            .map {
                it.last()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> pinDepartmentsInTask -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun pinWorkingCentersInTask(input: WorkingCenterWithTask, token: UUID): Optional<Task> =
        Flux.fromIterable(input.workingCenterIds ?: emptyList())
            .flatMap { id ->
                val entrant = WorkingCenterWithTaskInput(taskId = input.taskId, workingCenterId = UUID.fromString(id))
                return@flatMap database.sql(TaskDBQueries.pinWorkingCenters(input = entrant, token = token))
                    .map { row, meta -> TaskDBHandler.one(row = row, meta = meta) }
                    .first()
            }
            .collectList()
            .map {
                it.last()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> pinWorkingCentersInTask -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun pinUsersInTask(input: UserWithTask, token: UUID): Optional<Task> =
        Flux.fromIterable(input.userIds ?: emptyList())
            .flatMap { id ->
                val entrant = UserWithTaskInput(taskId = input.taskId, userId = UUID.fromString(id))
                return@flatMap database.sql(TaskDBQueries.pinUsers(input = entrant, token = token))
                    .map { row, meta -> TaskDBHandler.one(row = row, meta = meta) }
                    .first()
            }
            .collectList()
            .map {
                it.last()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> pinUsersInTask -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun unpinDepartmentsInTask(input: DepartmentWithTask, token: UUID): Optional<Task> =
        Flux.fromIterable(input.departmentIds ?: emptyList())
            .flatMap { id ->
                val entrant = DepartmentWithTaskInput(taskId = input.taskId, departmentId = UUID.fromString(id))
                return@flatMap database.sql(TaskDBQueries.unpinDepartments(input = entrant, token = token))
                    .map { row, meta -> TaskDBHandler.one(row = row, meta = meta) }
                    .first()
            }
            .collectList()
            .map {
                it.last()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> pinDepartmentsInTask -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun unpinWorkingCentersInTask(input: WorkingCenterWithTask, token: UUID): Optional<Task> =
        Flux.fromIterable(input.workingCenterIds ?: emptyList())
            .flatMap { id ->
                val entrant = WorkingCenterWithTaskInput(taskId = input.taskId, workingCenterId = UUID.fromString(id))
                return@flatMap database.sql(TaskDBQueries.unpinWorkingCenters(input = entrant, token = token))
                    .map { row, meta -> TaskDBHandler.one(row = row, meta = meta) }
                    .first()
            }
            .collectList()
            .map {
                it.last()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> pinWorkingCentersInTask -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun unpinUsersInTask(input: UserWithTask, token: UUID): Optional<Task> =
        Flux.fromIterable(input.userIds ?: emptyList())
            .flatMap { id ->
                val entrant = UserWithTaskInput(taskId = input.taskId, userId = UUID.fromString(id))
                return@flatMap database.sql(TaskDBQueries.unpinUsers(input = entrant, token = token))
                    .map { row, meta -> TaskDBHandler.one(row = row, meta = meta) }
                    .first()
            }
            .collectList()
            .map {
                it.last()
            }
            .onErrorResume {
                logger.warn { "+TaskService -> pinUsersInTask -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun retrieveTasksByGroupId(input: UUID, token: UUID): Optional<DraggableMap> {
        val tasksFlux = database.sql(TaskDBQueries.myTaskByGroupId(instanceId = input, token = token))
            .map { row, meta -> TaskDBHandler.all(row = row, meta = meta) }
            .first()
            .flatMap { list ->
                val ids = list.map { task -> task.id }.toSet()
                val outcome = list.associateBy({ it.id }, { it }).toMutableMap()
                Mono.zip(innerOperation(ids = ids), innerOrder(ids = ids)) { operations, orders ->
                    operations.forEach { (key, value) ->
                        outcome[key]?.operation = value
                    }
                    orders.forEach { (key, value) ->
                        outcome[key]?.order = value
                    }
                }.zipWith(innerItem(ids = ids, userId = UUID.randomUUID(), first = 100, after = null)) { o, items ->
                    items.forEach { (key, value) ->
                        outcome[key]?.item = value
                    }
                }.map { outcome }
            }

        val wcs = database.sql(TaskDBQueries.myWorkCenterAndTaskByGroupId(instanceId = input, token = token))
            .map { row -> WCAndTaskDBHandler.all(row = row) }
            .first()

        return Mono.zip(tasksFlux, wcs) { myTasks, workcenters ->
            val list = mutableListOf<UUID>()
            workcenters.forEach { i -> list.addAll(i.tasksIds.map { _id -> UUID.fromString(_id) }) }
            val tIds = myTasks.filterNot { list.contains(it.key) }.values.map { "${it.id}" }.toList()
            val outcome: MutableList<DraggableWorkcenter> = workcenters.toMutableList()
            outcome.add(DraggableWorkcenter(id = input, title = "Default", tasksIds = tIds))

            Optional.of(
                DraggableMap(
                    tasks = myTasks,
                    columns = outcome.associateBy({ it.id }, { it }),
                    columnOrder = outcome.map { it.id }.toSet().toList().reversed()
                )
            )
        }.onErrorResume {
            logger.warn { "+TaskService -> pinUsersInTask -> error = ${it.message}" }
            throw it
        }.awaitFirstOrElse { Optional.empty() }

    }

    override suspend fun retrieveTasksById(input: UUID, token: UUID): Optional<Task> =
        database.sql(TaskDBQueries.retrieveTaskById(taskId = input, token = token))
            .map { row, meta -> TaskDBHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+TaskService -> retrieveTasksById -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun editTask(input: TaskInput, token: UUID): Optional<Task> =
        database.sql(TaskDBQueries.editTask(input = input, token = token))
            .map { row, meta -> TaskDBHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+TaskService -> editTask -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun terminateTask(id: UUID, token: UUID): Optional<Task> =
        database.sql(TaskDBQueries.terminateTask(taskId = id, token = token))
            .map { row -> TaskDBHandler.one(row = row) }
            .first()
            .onErrorResume {
                logger.warn { "+TaskService -> terminateTask -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun retrieveTasksByUserId(userId: UUID, first: Int, after: UUID?, token: UUID): List<Task> =
        database.sql(TaskDBQueries.retrieveTaskByUserId(userId = userId, first = first, after = after, token = token))
            .map { row -> TaskDBHandler.all(row = row) }
            .first()
            .onErrorResume {
                logger.warn { "+TaskService -> retrieveTasksByUserId -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyList() }

    override suspend fun retrieveTasksByDepartmentId(
        departmentId: UUID,
        first: Int,
        after: UUID?,
        token: UUID,
        level: TaskLevel?
    ): List<Task> = fnTasksByDepartmentId(
        departmentId = departmentId,
        first = first,
        after = after,
        token = token,
        level = level,
        database = database
    )

    override suspend fun retrieveTasksByWorkingCenterId(
        workingCenterId: UUID,
        first: Int,
        after: UUID?,
        token: UUID,
        level: TaskLevel?
    ): List<Task> = fnTasksByWorkingCenterId(
        workingCenterId = workingCenterId,
        first = first,
        after = after,
        token = token,
        level = level,
        database = database
    )

    override suspend fun retrieveTasksByUserToken(
        first: Int,
        after: UUID?,
        token: UUID,
        level: TaskLevel?
    ): List<Task> = fnTasksByUserToken(
        first = first,
        after = after,
        token = token,
        level = level,
        database = database
    )

    override suspend fun retrieveNumberOfTaskByUserId(userId: UUID, token: UUID): Optional<Int> =
        database.sql(TaskDBQueries.retrieveNumberOfTaskByUserId(userId = userId, token = token))
            .map { row, meta -> TaskDBHandler.count(row = row, meta = meta) }
            .first()
            .map {
                Optional.ofNullable(it)
            }
            .onErrorResume {
                logger.warn { "+TaskService ->retrieveNumberOfTaskByUserId -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun createOrEdit(input: TaskInput, token: UUID): Optional<Task> =
        database.sql(TaskDBQueries.createOrEdit(input = input, token = token))
            .map { row, meta -> TaskDBHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+TaskService->createOrEdit->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun delete(input: TaskInput, token: UUID): Optional<Order> =
        database.sql(TaskDBQueries.delete(input = input, token = token))
            .map { row, meta -> OrderDBHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+TaskService->delete->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun departments(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Group>> =
        retrieveDepartmentsUseCase(database = database, ids = ids, token = token, first = first, after = after)

    override suspend fun item(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Item>> =
        findItemUseCase(database = database, ids = ids, token = token)

    override suspend fun operation(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Operation>> =
        findOperationUseCase(database = database, ids = ids, token = token)

    override suspend fun order(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Order>> =
        emptyMap()

    override suspend fun parameters(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Parameter>> =
        retrieveParametersUseCase(database = database, ids = ids, first = first, after = after, token = token)
}