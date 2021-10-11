package  com.rsba.order_microservice.database


import com.rsba.order_microservice.domain.input.DepartmentWithTaskInput
import com.rsba.order_microservice.domain.input.TaskInput
import com.rsba.order_microservice.domain.input.UserWithTaskInput
import com.rsba.order_microservice.domain.input.WorkingCenterWithTaskInput
import com.rsba.order_microservice.domain.model.TaskLevel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object TaskDBQueries {
    fun ourPersonalInfo(itemId: UUID) =
        "SELECT on_retrieve_operations_by_item_id('$itemId')"

    fun myContactInfo(itemId: UUID) =
        "SELECT on_retrieve_tasks_by_item_id('$itemId')"

    fun myUsers(workcenterId: UUID, taskId: UUID) =
        "SELECT on_retrieve_users_of_workcenter_in_task('$workcenterId', '$taskId')"

    fun userOfTask(taskId: UUID) =
        "SELECT on_retrieve_users_by_task_id('$taskId')"

    fun myWorkingCenters(groupId: UUID, taskId: UUID) =
        "SELECT on_retrieve_workcenter_of_department_in_task('$groupId', '$taskId')"

    fun myDepartments(taskId: UUID) =
        "SELECT on_retrieve_departments_by_task_id('$taskId')"

    fun myItem(taskId: UUID) =
        "SELECT on_retrieve_item_by_task_id('$taskId')"

    fun myOperation(taskId: UUID) =
        "SELECT on_retrieve_operation_by_task_id('$taskId')"

    fun myOrder(taskId: UUID) =
        "SELECT on_retrieve_order_by_task_id('$taskId')"

    fun myComments(taskId: UUID) =
        "SELECT on_retrieve_comments_by_task_id('$taskId')"

    fun pinDepartments(input: DepartmentWithTaskInput, token: UUID) =
        "SELECT on_pin_department_to_task('${Json.encodeToString(input)}', '$token')"

    fun pinWorkingCenters(input: WorkingCenterWithTaskInput, token: UUID) =
        "SELECT on_pin_working_center_to_task('${Json.encodeToString(input)}', '$token')"

    fun pinUsers(input: UserWithTaskInput, token: UUID) =
        "SELECT on_pin_user_to_task('${Json.encodeToString(input)}', '$token')"

    fun unpinDepartments(input: DepartmentWithTaskInput, token: UUID) =
        "SELECT on_unpin_department_to_task('${Json.encodeToString(input)}', '$token')"

    fun unpinWorkingCenters(input: WorkingCenterWithTaskInput, token: UUID) =
        "SELECT on_unpin_working_center_to_task('${Json.encodeToString(input)}', '$token')"

    fun unpinUsers(input: UserWithTaskInput, token: UUID) =
        "SELECT on_unpin_user_to_task('${Json.encodeToString(input)}', '$token')"

    fun myTaskByGroupId(instanceId: UUID, token: UUID) =
        "SELECT on_retrieve_tasks_by_group_id('$instanceId', '$token')"

    fun myWorkCenterAndTaskByGroupId(instanceId: UUID, token: UUID) =
        "SELECT on_retrieve_workcenter_with_task_by_group_id('$instanceId', '$token')"

    fun retrieveTaskById(taskId: UUID, token: UUID) =
        "SELECT on_retrieve_tasks_by_id('$taskId', '$token')"

    fun editTask(input: TaskInput, token: UUID) =
        "SELECT on_edit_task('${Json.encodeToString(input)}', '$token')"

    fun terminateTask(taskId: UUID, token: UUID) =
        "SELECT on_terminate_task('$taskId', '$token')"

    fun retrieveTaskByUserId(userId: UUID, first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_tasks_by_user_id('$userId', '$first', ${after?.let { "'$it'" }},'$token')"

    fun retrieveTaskByUserToken(first: Int, after: UUID?, token: UUID, level: TaskLevel? = null) =
        "SELECT on_retrieve_tasks_by_user_token('$first', ${after?.let { "'$it'" }}, ${level?.let { "'$it'" }},'$token')"

    fun retrieveTaskByDepartmentId(
        departmentId: UUID,
        first: Int,
        after: UUID?,
        token: UUID,
        level: TaskLevel? = null
    ) =
        "SELECT on_retrieve_tasks_by_department_id('$departmentId','$first', ${after?.let { "'$it'" }}, ${level?.let { "'$it'" }},'$token')"

    fun retrieveTaskByWorkingCenterId(
        workingCenterId: UUID,
        first: Int,
        after: UUID?,
        token: UUID,
        level: TaskLevel? = null
    ) =
        "SELECT on_retrieve_tasks_by_working_center_id('$workingCenterId','$first', ${after?.let { "'$it'" }}, ${level?.let { "'$it'" }},'$token')"

    fun retrieveNumberOfTaskByUserId(userId: UUID, token: UUID) =
        "SELECT on_retrieve_number_of_task_by_user_id('$userId', '$token')"

    fun createOrEdit(input: TaskInput, token: UUID) =
        "SELECT on_create_or_edit_task('${Json.encodeToString(input)}', '$token')"

    fun delete(input: TaskInput, token: UUID) =
        "SELECT on_delete_task_in_item_in_order('${Json.encodeToString(input)}', '$token')"
}
