package  com.rsba.tasks_microservice.data.service.usecase.queries

import com.rsba.tasks_microservice.data.dao.TaskDao
import com.rsba.tasks_microservice.data.dao.TaskSetDao
import com.rsba.tasks_microservice.domain.queries.IBaseQuery
import com.rsba.tasks_microservice.domain.format.JsonHandlerKotlin
import com.rsba.tasks_microservice.domain.queries.QueryBuilder
import com.rsba.tasks_microservice.domain.input.TaskSetInput
import com.rsba.tasks_microservice.domain.model.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.time.OffsetDateTime
import java.util.*

@ExperimentalSerializationApi
object TaskSetQueries : IBaseQuery<TaskSetInput, TaskSetDao> {

    override fun createOrEdit(input: TaskSetInput, token: UUID, action: MutationAction?, case: Edition?): String =
        buildString {
            append(QueryBuilder.CreateOrEdit.buildRequestDef<TaskSetDao>())
            append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
            append("${action?.let { "'$it'" }},")
            append("'$token')")
        }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<TaskSetDao>())
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
        append(QueryBuilder.Retrieve.buildRequestDef<TaskSetDao>())
        append("($first,")
        append("${id?.let { "'$it'" }},")
        append("${after?.let { "'$it'" }},")
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
        append(QueryBuilder.Search.buildRequestDef<TaskSetDao>())
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
        append(QueryBuilder.Find.buildRequestDef<TaskSetDao>())
        append("('$id',")
        append("'$token')")
    }

    override fun count(status: TaskStatus?, layer: TaskLayer?, id: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<TaskSetDao>())
        append("('$token')")
    }

    fun users(id: UUID, first: Int, after: UUID? = null, token: UUID): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<TaskSetDao>(customQuery = "_on_retrieve_users"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("'$token')")
        }

    fun tasks(id: UUID, first: Int, after: UUID? = null, token: UUID): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<TaskSetDao>(customQuery = "_on_retrieve_tasks"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("'$token')")
        }

    fun comments(
        id: UUID, first: Int, after: UUID? = null, token: UUID,
        layer: CommentLayer? = null
    ): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<TaskSetDao>(customQuery = "_on_retrieve_comments"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("${layer?.let { "'$it'" }},")
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

}
