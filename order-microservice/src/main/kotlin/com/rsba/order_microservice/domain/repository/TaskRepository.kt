package  com.rsba.order_microservice.domain.repository

import com.rsba.order_microservice.domain.input.DepartmentWithTask
import com.rsba.order_microservice.domain.input.TaskInput
import com.rsba.order_microservice.domain.input.UserWithTask
import com.rsba.order_microservice.domain.input.WorkingCenterWithTask
import com.rsba.order_microservice.domain.model.*
import java.util.*

interface TaskRepository {

    suspend fun myPersonalInfo(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, PersonalInfo?>

    suspend fun myContactInfo(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, List<ContactInfo>>

    suspend fun myUsers(
        ids: Set<WorkingCenter>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<WorkingCenter, List<User>>

    suspend fun usersInTask(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, List<User>>

    suspend fun myWorkingCenters(
        ids: Set<Department>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<Department, List<WorkingCenter>>

    suspend fun myDepartments(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, List<Department>>

    suspend fun myItem(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, Item?>

    suspend fun myOrder(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, Order?>

    suspend fun myOperation(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, Operation?>

    suspend fun myComments(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, List<Comment>>

    suspend fun pinDepartmentsInTask(input: DepartmentWithTask, token: UUID): Optional<Task>
    suspend fun pinWorkingCentersInTask(input: WorkingCenterWithTask, token: UUID): Optional<Task>
    suspend fun pinUsersInTask(input: UserWithTask, token: UUID): Optional<Task>
    suspend fun unpinDepartmentsInTask(input: DepartmentWithTask, token: UUID): Optional<Task>
    suspend fun unpinWorkingCentersInTask(input: WorkingCenterWithTask, token: UUID): Optional<Task>
    suspend fun unpinUsersInTask(input: UserWithTask, token: UUID): Optional<Task>
    suspend fun retrieveTasksByGroupId(input: UUID, token: UUID): Optional<DraggableMap>
    suspend fun retrieveTasksById(input: UUID, token: UUID): Optional<Task>
    suspend fun editTask(input: TaskInput, token: UUID): Optional<Task>
    suspend fun terminateTask(id: UUID, token: UUID): Optional<Task>
    suspend fun retrieveTasksByUserId(userId: UUID, first: Int, after: UUID?, token: UUID): List<Task>

    suspend fun retrieveTasksByUserToken(first: Int, after: UUID?, token: UUID, level: TaskLevel? = null): List<Task>
    suspend fun retrieveTasksByDepartmentId(
        departmentId: UUID,
        first: Int,
        after: UUID?,
        token: UUID,
        level: TaskLevel? = null
    ): List<Task>

    suspend fun retrieveTasksByWorkingCenterId(
        workingCenterId: UUID,
        first: Int,
        after: UUID?,
        token: UUID,
        level: TaskLevel? = null
    ): List<Task>


    suspend fun retrieveNumberOfTaskByUserId(userId: UUID, token: UUID): Optional<Int>
    suspend fun createOrEdit(input: TaskInput, token: UUID): Optional<Task>
    suspend fun delete(input: TaskInput, token: UUID): Optional<Order>


    suspend fun departments(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Group>>

    suspend fun item(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Item>>
    suspend fun operation(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Operation>>
    suspend fun order(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Order>>

    suspend fun parameters(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Parameter>>
}
