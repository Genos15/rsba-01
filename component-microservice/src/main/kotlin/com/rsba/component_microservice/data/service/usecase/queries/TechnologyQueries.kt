package  com.rsba.component_microservice.data.service.usecase.queries

import com.rsba.component_microservice.data.dao.TechnologyDao
import com.rsba.component_microservice.domain.queries.IBaseQuery
import com.rsba.component_microservice.domain.format.JsonHandlerKotlin
import com.rsba.component_microservice.domain.queries.QueryBuilder
import com.rsba.component_microservice.domain.input.TechnologyInput
import com.rsba.component_microservice.domain.model.Edition
import com.rsba.component_microservice.domain.model.MutationAction
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.util.*

@ExperimentalSerializationApi
object TechnologyQueries : IBaseQuery<TechnologyInput, TechnologyDao> {

    override fun createOrEdit(
        input: TechnologyInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?
    ): String = buildString {
        append(QueryBuilder.CreateOrEdit.buildRequestDef<TechnologyDao>())
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
        append("${action?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<TechnologyDao>())
        append("('$input',")
        append("'$token')")
    }

    override fun retrieve(first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<TechnologyDao>())
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun search(input: String, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<TechnologyDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<TechnologyDao>())
        append("('$id',")
        append("'$token')")
    }

    override fun count(token: UUID): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<TechnologyDao>())
        append("('$token')")
    }

    fun operations(id: UUID, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<TechnologyDao>(customQuery = "_on_retrieve_operations"))
        append("('$id',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

}
