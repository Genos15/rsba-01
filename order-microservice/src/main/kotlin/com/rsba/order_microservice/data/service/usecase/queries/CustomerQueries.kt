package  com.rsba.order_microservice.data.service.usecase.queries

import com.rsba.order_microservice.data.dao.CustomerDao
import com.rsba.order_microservice.domain.format.JsonHandlerKotlin
import com.rsba.order_microservice.domain.input.CustomerInput
import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.queries.IBaseQuery
import com.rsba.order_microservice.domain.queries.QueryBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.util.*

@ExperimentalSerializationApi
object CustomerQueries : IBaseQuery<CustomerInput, CustomerDao> {

    override fun createOrEdit(
        input: CustomerInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?
    ): String = buildString {
        append(QueryBuilder.CreateOrEdit.buildRequestDef<CustomerDao>())
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
        append("${action?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<CustomerDao>())
        append("('$input',")
        append("'$token')")
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<CustomerDao>())
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
        append(QueryBuilder.Retrieve.buildRequestDef<CustomerDao>())
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
        append(QueryBuilder.Search.buildRequestDef<CustomerDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun count(token: UUID, status: AbstractStatus?): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<CustomerDao>())
        append("('$token')")
    }

    fun entities(id: UUID, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<CustomerDao>(customQuery = "_on_retrieve_entities"))
        append("('$id',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

}
