package  com.rsba.component_microservice.data.service.usecase.queries

import com.rsba.component_microservice.data.dao.OperationDao
import com.rsba.component_microservice.domain.queries.IBaseQuery
import com.rsba.component_microservice.domain.format.JsonHandlerKotlin
import com.rsba.component_microservice.domain.queries.QueryBuilder
import com.rsba.component_microservice.domain.input.OperationInput
import com.rsba.component_microservice.domain.model.Edition
import com.rsba.component_microservice.domain.model.MutationAction
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.util.*

@ExperimentalSerializationApi
object OperationQueries : IBaseQuery<OperationInput, OperationDao> {

    override fun createOrEdit(
        input: OperationInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?
    ): String =
        buildString {
            append(QueryBuilder.CreateOrEdit.buildRequestDef<OperationDao>())
            append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
            append("${action?.let { "'$it'" }},")
            append("${case?.let { "'${JsonHandlerKotlin.handler.encodeToString(case)}'" }},")
            append("'$token')")
        }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<OperationDao>())
        append("('$input',")
        append("'$token')")
    }

    override fun retrieve(first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<OperationDao>())
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun search(input: String, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<OperationDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<OperationDao>())
        append("('$id',")
        append("'$token')")
    }

    override fun count(token: UUID): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<OperationDao>())
        append("('$token')")
    }

    fun departments(id: UUID, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<OperationDao>(customQuery = "_on_retrieve_departments"))
        append("('$id',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }


}
