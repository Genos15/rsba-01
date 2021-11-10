package  com.rsba.component_microservice.data.service.usecase.queries

import com.rsba.component_microservice.data.dao.ItemCategoryDao
import com.rsba.component_microservice.domain.format.IBaseQuery
import com.rsba.component_microservice.domain.format.QueryBuilder
import com.rsba.component_microservice.domain.input.ItemCategoryInput
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

@ExperimentalSerializationApi
object ItemCategoryQueries : IBaseQuery<ItemCategoryInput, ItemCategoryDao> {

    override fun createOrEdit(input: ItemCategoryInput, token: UUID): String = buildString {
        append(QueryBuilder.CreateOrEdit.buildRequestDef<ItemCategoryDao>())
        append("('${Json.encodeToString(input)}',")
        append("'$token')")
    }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<ItemCategoryDao>())
        append("('$input',")
        append("'$token')")
    }

    override fun retrieve(first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<ItemCategoryDao>())
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun search(input: String, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<ItemCategoryDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<ItemCategoryDao>())
        append("('$id',")
        append("'$token')")
    }

    fun children(id: UUID, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemCategoryDao>(customQuery = "_on_retrieve_children"))
        append("('$id',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    fun children(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemCategoryDao>(customQuery = "_on_retrieve_children_dataloader"))
        append("('$id',")
        append("'$token')")
    }

    fun items(id: UUID, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemCategoryDao>(customQuery = "_on_retrieve_items_dataloader"))
        append("('$id',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun count(token: UUID): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<ItemCategoryDao>())
        append("('$token')")
    }

}
