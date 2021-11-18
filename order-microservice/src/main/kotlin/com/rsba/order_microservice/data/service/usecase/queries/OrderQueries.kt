package  com.rsba.order_microservice.data.service.usecase.queries

import com.rsba.order_microservice.data.dao.OrderDao
import com.rsba.order_microservice.domain.format.JsonHandlerKotlin
import com.rsba.order_microservice.domain.input.OrderInput
import com.rsba.order_microservice.domain.model.AbstractLayer
import com.rsba.order_microservice.domain.model.AbstractStatus
import com.rsba.order_microservice.domain.model.Edition
import com.rsba.order_microservice.domain.model.MutationAction
import com.rsba.order_microservice.domain.queries.IBaseQuery
import com.rsba.order_microservice.domain.queries.QueryBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.util.*

@ExperimentalSerializationApi
object OrderQueries : IBaseQuery<OrderInput, OrderDao> {

    fun completionLineGraph(year: Int, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OrderDao>(customQuery = "_on_completion_line_graph"))
        append("($year,")
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
        layer: AbstractLayer?,
        status: AbstractStatus?,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<OrderDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("${layer?.let { "'$it'" }},")
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
        layer: AbstractLayer?,
        status: AbstractStatus?
    ): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<OrderDao>())
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("${layer?.let { "'$it'" }},")
        append("${status?.let { "'$it'" }},")
        append("'$token')")
    }

}
