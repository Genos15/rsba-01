package  com.rsba.component_microservice.data.service.usecase.queries

import com.rsba.component_microservice.data.dao.ItemDao
import com.rsba.component_microservice.domain.format.IBaseQuery
import com.rsba.component_microservice.domain.format.QueryBuilder
import com.rsba.component_microservice.domain.input.ItemInput
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

@ExperimentalSerializationApi
object ItemQueries : IBaseQuery<ItemInput, ItemDao> {

    override fun createOrEdit(input: ItemInput, token: UUID): String = buildString {
        append(QueryBuilder.CreateOrEdit.buildRequestDef<ItemDao>())
        append("('${Json.encodeToString(input)}',")
        append("'$token')")
    }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<ItemDao>())
        append("('$input',")
        append("'$token')")
    }

    override fun retrieve(first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<ItemDao>())
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun search(input: String, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<ItemDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<ItemDao>())
        append("('$id',")
        append("'$token')")
    }

}
