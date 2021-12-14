package  com.rsba.parameters_microservice.data.service.usecase.queries

import com.rsba.parameters_microservice.data.dao.ParameterDao
import com.rsba.parameters_microservice.domain.queries.IBaseQuery
import com.rsba.parameters_microservice.domain.format.JsonHandlerKotlin
import com.rsba.parameters_microservice.domain.queries.QueryBuilder
import com.rsba.parameters_microservice.domain.input.ParameterInput
import com.rsba.parameters_microservice.domain.model.Edition
import com.rsba.parameters_microservice.domain.model.MutationAction
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.util.*

@ExperimentalSerializationApi
object ParameterQueries : IBaseQuery<ParameterInput, ParameterDao> {

    override fun createOrEdit(
        input: ParameterInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?
    ): String = buildString {
        append(QueryBuilder.CreateOrEdit.buildRequestDef<ParameterDao>())
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
        append("${action?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<ParameterDao>())
        append("('$input',")
        append("'$token')")
    }

    override fun retrieve(first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<ParameterDao>())
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun search(input: String, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<ParameterDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<ParameterDao>())
        append("('$id',")
        append("'$token')")
    }

    override fun count(token: UUID): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<ParameterDao>())
        append("('$token')")
    }

    fun values(id: UUID, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ParameterDao>(customQuery = "_on_retrieve_values"))
        append("('$id',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

}
