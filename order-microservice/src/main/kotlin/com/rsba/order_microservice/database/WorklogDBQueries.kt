package  com.rsba.order_microservice.database


import java.util.*

object WorklogDBQueries {

    fun retrieveWorklogByTaskId(taskId: UUID, first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_worklogs_by_task_id('$taskId','$first', ${after?.let { "'$it'" }},'$token')"

    fun myActor(input: UUID, token: UUID) =
        "SELECT on_retrieve_actor_by_worklog_id('$input', '$token')"
}
