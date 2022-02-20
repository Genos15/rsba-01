package  com.rsba.tasks_microservice.data.service.usecase.queries

import com.rsba.tasks_microservice.data.dao.TaskDao
import com.rsba.tasks_microservice.domain.queries.IBaseQuery
import com.rsba.tasks_microservice.domain.format.JsonHandlerKotlin
import com.rsba.tasks_microservice.domain.queries.QueryBuilder
import com.rsba.tasks_microservice.domain.input.TaskInput
import com.rsba.tasks_microservice.domain.input.TaskWorkerTimeInput
import com.rsba.tasks_microservice.domain.model.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.time.OffsetDateTime
import java.util.*

@ExperimentalSerializationApi
object TaskQueries : IBaseQuery<TaskInput, TaskDao> {

    override fun createOrEdit(
        input: TaskInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?
    ): String = buildString {
        append(QueryBuilder.CreateOrEdit.buildRequestDef<TaskDao>())
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
        append("${action?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<TaskDao>())
        append("('$input',")
        append("'$token')")
    }

    override fun retrieve(
        first: Int,
        after: UUID?,
        status: TaskStatus?,
        layer: TaskLayer?,
        id: UUID?,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<TaskDao>())
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("${status?.let { "'$it'" }},")
        append("${layer?.let { "'$it'" }},")
        append("${id?.let { "'$it'" }},")
        append("${rangeStart?.let { "'$rangeStart'" }},")
        append("${rangeEnd?.let { "'$rangeEnd'" }},")
        append("'$token')")
    }

    override fun search(
        input: String,
        first: Int,
        after: UUID?,
        status: TaskStatus?,
        layer: TaskLayer?,
        id: UUID?,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<TaskDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("${status?.let { "'$it'" }},")
        append("${layer?.let { "'$it'" }},")
        append("${id?.let { "'$it'" }},")
        append("${rangeStart?.let { "'$rangeStart'" }},")
        append("${rangeEnd?.let { "'$rangeEnd'" }},")
        append("'$token')")
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<TaskDao>())
        append("('$id',")
        append("'$token')")
    }

    override fun count(status: TaskStatus?, layer: TaskLayer?, id: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<TaskDao>())
        append("(${status?.let { "'$it'" }},")
        append("${layer?.let { "'$it'" }},")
        append("${id?.let { "'$it'" }},")
        append("'$token')")
    }

    fun order(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_find_order"))
        append("('$id',")
        append("'$token')")
    }

    fun item(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_find_item"))
        append("('$id',")
        append("'$token')")
    }

    fun operation(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_find_operation"))
        append("('$id',")
        append("'$token')")
    }

    fun workcenter(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_find_workcenter"))
        append("('$id',")
        append("'$token')")
    }

    fun allocate(
        id: UUID,
        users: List<TaskWorkerTimeInput>,
        action: MutationAction? = null,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_allocate"))
        append("('$id',")
        append("'${JsonHandlerKotlin.handler.encodeToString(users)}',")
        append("${action?.let { "'$it'" }},")
        append("'$token')")
    }

    fun execute(
        id: UUID,
        quantity: Int? = null,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_execute"))
        append("('$id',")
        append("${quantity?.let { "$it" }},")
        append("'$token')")
    }


    fun executeTasksOfItem(
        input: UUID,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_execute_of_item"))
        append("('$input',")
        append("'$token')")
    }

    fun users(id: UUID, first: Int, after: UUID? = null, token: UUID): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_retrieve_users"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("'$token')")
        }

    fun comments(id: UUID, first: Int, after: UUID? = null, token: UUID, layer: CommentLayer? = null): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_retrieve_comments"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("${layer?.let { "'$it'" }},")
            append("'$token')")
        }

    fun technologies(id: UUID, first: Int, after: UUID? = null, token: UUID): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_retrieve_technologies"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("'$token')")
        }

    fun worklogs(id: UUID, first: Int, after: UUID? = null, token: UUID): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_retrieve_worklogs"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("'$token')")
        }

    fun userWorkload(
        id: UUID,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        token: UUID
    ): String = buildString {
        append("select users_on_calculate_workload")
        append("('$id',")
        append("${rangeStart.let { "'$rangeStart'" }},")
        append("${rangeEnd.let { "'$rangeEnd'" }},")
        append("'$token')")
    }

    fun workcenterWorkload(
        id: UUID,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        token: UUID
    ): String = buildString {
        append("select workcenter_on_calculate_workload")
        append("('$id',")
        append("${rangeStart.let { "'$rangeStart'" }},")
        append("${rangeEnd.let { "'$rangeEnd'" }},")
        append("'$token')")
    }

    fun userActivities(
        first: Int,
        after: UUID?,
        layer: UserActivityLayer,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        token: UUID
    ): String = buildString {
        append("select users_on_retrieve_amount_activities")
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("${layer.let { "'$it'" }},")
        append("${rangeStart.let { "'$rangeStart'" }},")
        append("${rangeEnd.let { "'$rangeEnd'" }},")
        append("'$token')")
    }
}
