package  com.rsba.order_microservice.data.service.usecase.queries

import com.rsba.order_microservice.data.dao.OrderTypeDao
import com.rsba.order_microservice.domain.format.JsonHandlerKotlin
import com.rsba.order_microservice.domain.input.OrderTypeInput
import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.queries.IBaseQuery
import com.rsba.order_microservice.domain.queries.QueryBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.util.*

@ExperimentalSerializationApi
object OrderTypeQueries : IBaseQuery<OrderTypeInput, OrderTypeDao> {

    override fun createOrEdit(
        input: OrderTypeInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?
    ): String = buildString {
        append(QueryBuilder.CreateOrEdit.buildRequestDef<OrderTypeDao>())
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
        append("${action?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<OrderTypeDao>())
        append("('$input',")
        append("'$token')")
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<OrderTypeDao>())
        append("('$id',")
        append("'$token')")
    }

    override fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        layer: OrderLayer?,
        status: AbstractStatus?
    ): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<OrderTypeDao>())
        append("($first,")
        append("${after?.let { "'$it'" }},")
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
        append(QueryBuilder.Search.buildRequestDef<OrderTypeDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun count(token: UUID, status: AbstractStatus?): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<OrderTypeDao>())
        append("('$token')")
    }
}
