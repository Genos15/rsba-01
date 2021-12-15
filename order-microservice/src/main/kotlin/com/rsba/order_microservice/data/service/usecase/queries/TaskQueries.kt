package  com.rsba.order_microservice.data.service.usecase.queries

import com.rsba.order_microservice.data.dao.TaskDao
import com.rsba.order_microservice.domain.format.JsonHandlerKotlin
import com.rsba.order_microservice.domain.input.TaskInput
import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.queries.IBaseQuery
import com.rsba.order_microservice.domain.queries.QueryBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.util.*

@ExperimentalSerializationApi
object TaskQueries : IBaseQuery<TaskInput, TaskDao> {

    override fun createOrEdit(input: TaskInput, token: UUID, action: MutationAction?, case: Edition?): String =
        buildString {
            append(QueryBuilder.CreateOrEdit.buildRequestDef<TaskDao>())
            append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
            append("${action?.let { "'$it'" }},")
            append("'$token')")
        }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<TaskDao>())
    }

    override fun search(
        input: String,
        first: Int,
        after: UUID?,
        layer: OrderLayer?,
        status: AbstractStatus?,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<TaskDao>())
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<TaskDao>())
    }

    override fun count(token: UUID, status: AbstractStatus?): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<TaskDao>())
    }

    override fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        layer: OrderLayer?,
        status: AbstractStatus?
    ): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<TaskDao>())
    }

    fun departments(id: UUID, first: Int, after: UUID? = null, token: UUID): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_retrieve_departments"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("'$token')")
        }

    fun operation(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_find_operation"))
        append("('$id',")
        append("'$token')")
    }

    fun item(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_find_item"))
        append("('$id',")
        append("'$token')")
    }

    fun parameters(id: UUID, first: Int, after: UUID? = null, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<TaskDao>(customQuery = "_on_retrieve_parameters"))
        append("('$id',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

}
