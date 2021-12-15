package  com.rsba.order_microservice.data.service.usecase.queries

import com.rsba.order_microservice.data.dao.ItemDao
import com.rsba.order_microservice.domain.format.JsonHandlerKotlin
import com.rsba.order_microservice.domain.input.ItemInput
import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.queries.IBaseQuery
import com.rsba.order_microservice.domain.queries.QueryBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.util.*

@ExperimentalSerializationApi
object ItemQueries : IBaseQuery<ItemInput, ItemDao> {

    override fun createOrEdit(input: ItemInput, token: UUID, action: MutationAction?, case: Edition?): String =
        buildString {
            append(QueryBuilder.CreateOrEdit.buildRequestDef<ItemDao>())
            append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
            append("${action?.let { "'$it'" }},")
            append("'$token')")
        }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<ItemDao>())
    }

    override fun search(
        input: String,
        first: Int,
        after: UUID?,
        layer: OrderLayer?,
        status: AbstractStatus?,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<ItemDao>())
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<ItemDao>())
    }

    override fun count(token: UUID, status: AbstractStatus?): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<ItemDao>())
    }

    override fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        layer: OrderLayer?,
        status: AbstractStatus?
    ): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<ItemDao>())
    }


    fun statistics(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_find_statistics"))
        append("('$id',")
        append("'$token')")
    }

    fun whoAdded(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_find_who_added"))
        append("('$id',")
        append("'$token')")
    }

    fun technologies(id: UUID, first: Int, after: UUID? = null, token: UUID): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_retrieve_technologies"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("'$token')")
        }

    fun elk(token: UUID, from: UUID? = null, orderId: UUID? = null): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_retrieve_elk_elements"))
        append("(${from?.let { "'$it'" }},")
        append("${orderId?.let { "'$it'" }},")
        append("'$token')")
    }

    fun parameters(id: UUID, first: Int, after: UUID? = null, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_retrieve_parameters"))
        append("('$id',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

}
