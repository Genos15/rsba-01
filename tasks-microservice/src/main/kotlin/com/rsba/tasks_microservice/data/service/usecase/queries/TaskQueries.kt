package  com.rsba.tasks_microservice.data.service.usecase.queries

import com.rsba.tasks_microservice.data.dao.TaskDao
import com.rsba.tasks_microservice.domain.queries.IBaseQuery
import com.rsba.tasks_microservice.domain.format.JsonHandlerKotlin
import com.rsba.tasks_microservice.domain.queries.QueryBuilder
import com.rsba.tasks_microservice.domain.input.TaskInput
import com.rsba.tasks_microservice.domain.model.Edition
import com.rsba.tasks_microservice.domain.model.MutationAction
import com.rsba.tasks_microservice.domain.model.TaskLayer
import com.rsba.tasks_microservice.domain.model.TaskStatus
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
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
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<TaskDao>())
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("${status?.let { "'$it'" }},")
        append("${layer?.let { "'$it'" }},")
        append("${id?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun search(
        input: String,
        first: Int,
        after: UUID?,
        status: TaskStatus?,
        layer: TaskLayer?,
        id: UUID?,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<TaskDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("${status?.let { "'$it'" }},")
        append("${layer?.let { "'$it'" }},")
        append("${id?.let { "'$it'" }},")
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

}
