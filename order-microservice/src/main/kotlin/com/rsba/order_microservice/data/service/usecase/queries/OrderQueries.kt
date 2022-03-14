package  com.rsba.order_microservice.data.service.usecase.queries

import com.rsba.order_microservice.data.dao.OrderDao
import com.rsba.order_microservice.domain.format.JsonHandlerKotlin
import com.rsba.order_microservice.domain.input.ItemInput
import com.rsba.order_microservice.domain.input.OrderInput
import com.rsba.order_microservice.domain.input.TaskInput
import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.queries.IBaseQuery
import com.rsba.order_microservice.domain.queries.QueryBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.time.OffsetDateTime
import java.util.*

@ExperimentalSerializationApi
object OrderQueries : IBaseQuery<OrderInput, OrderDao> {

    fun completionLineGraph(year: Int, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_completion_line_graph"))
        append("($year,")
        append("'$token')")
    }

    fun potentialReferenceNumber(companyId: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_potential_reference"))
        append("(${companyId?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun createOrEdit(input: OrderInput, token: UUID, action: MutationAction?, case: Edition?): String =
        buildString {
            append(QueryBuilder.CreateOrEdit.buildRequestDef<OrderDao>())
            append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
            append("${action?.let { "'$it'" }},")
            append("'$token')")
        }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<OrderDao>())
        append("('$input',")
        append("'$token')")
    }

    override fun search(
        input: String,
        first: Int,
        after: UUID?,
        layer: OrderLayer?,
        status: AbstractStatus?,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<OrderDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("${(JsonHandlerKotlin.handler.encodeToString(layer) as? String?)?.let { "'$it'" }},")
        append("${status?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<OrderDao>())
        append("('$id',")
        append("'$token')")
    }

    override fun count(token: UUID, status: AbstractStatus?): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<OrderDao>())
        append("(${status?.let { "'$it'" }}, '$token')")
    }

    override fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        layer: OrderLayer?,
        status: AbstractStatus?
    ): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<OrderDao>())
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("${(JsonHandlerKotlin.handler.encodeToString(layer) as? String?)?.let { "'$it'" }},")
        append("${status?.let { "'$it'" }},")
        append("'$token')")
    }

    fun items(id: UUID, first: Int, after: UUID? = null, token: UUID, parentId: UUID? = null): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_retrieve_items"))
        append("('$id',")
        append("$first,")
        append("${parentId?.let { "'$it'" }},")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    fun technologies(id: UUID, first: Int, after: UUID? = null, token: UUID, parentId: UUID? = null): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_retrieve_technologies"))
            append("('$id',")
            append("$first,")
            append("${parentId?.let { "'$it'" }},")
            append("${after?.let { "'$it'" }},")
            append("'$token')")
        }

    fun tasks(id: UUID, first: Int, after: UUID? = null, token: UUID, parentId: UUID? = null): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_retrieve_tasks"))
        append("('$id',")
        append("$first,")
        append("${parentId?.let { "'$it'" }},")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    fun customer(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_retrieve_customer"))
        append("('$id',")
        append("'$token')")
    }

    fun agent(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_retrieve_agent"))
        append("('$id',")
        append("'$token')")
    }

    fun manager(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_retrieve_manager"))
        append("('$id',")
        append("'$token')")
    }

    fun type(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_retrieve_type"))
        append("('$id',")
        append("'$token')")
    }

    fun item(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_find_item"))
        append("('$id',")
        append("'$token')")
    }

    fun task(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_find_task"))
        append("('$id',")
        append("'$token')")
    }

    fun searchItems(input: OrderSearchInputValue): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_search_global_items"))
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}')")
    }

    fun searchTasks(input: OrderSearchInputValue): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_search_global_tasks"))
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}')")
    }

    fun searchTechnologies(input: OrderSearchInputValue): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_search_global_technologies"))
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}')")
    }

    fun searchParameters(input: OrderSearchInputValue): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_search_global_parameters"))
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}')")
    }

    fun editTask(input: TaskInput, token: UUID, action: MutationAction?): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_edit_task"))
            append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
            append("${action?.let { "'$it'" }},")
            append("'$token')")
        }

    fun editItem(input: ItemInput, token: UUID, action: MutationAction?): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_edit_item"))
            append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
            append("${action?.let { "'$it'" }},")
            append("'$token')")
        }

    fun statistics(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_find_statistics"))
        append("('$id',")
        append("'$token')")
    }

    fun departmentStatistics(id: UUID, first: Int, after: UUID? = null, token: UUID): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_retrieve_departments_statistics"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("'$token')")
        }

    fun itemCategoryStatistics(id: UUID, first: Int, after: UUID? = null, token: UUID): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_retrieve_item_category_statistics"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("'$token')")
        }

    fun parameters(id: UUID, first: Int, after: UUID? = null, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_retrieve_parameters"))
        append("('$id',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    fun worklogs(
        id: UUID,
        first: Int,
        after: UUID? = null,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null, token: UUID
    ): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_retrieve_worklogs"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("${rangeStart?.let { "'$rangeStart'" }},")
            append("${rangeEnd?.let { "'$rangeEnd'" }},")
            append("'$token')")
        }

}
