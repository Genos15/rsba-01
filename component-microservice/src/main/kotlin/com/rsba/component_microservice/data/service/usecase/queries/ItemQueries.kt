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

    fun attachOperation(input: ItemInput, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_attach_operations"))
        append("('${Json.encodeToString(input)}',")
        append("'$token')")
    }

    fun detachOperation(input: ItemInput, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_detach_operations"))
        append("('${Json.encodeToString(input)}',")
        append("'$token')")
    }

    fun attachSubItem(input: ItemInput, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_attach_components"))
        append("('${Json.encodeToString(input)}',")
        append("'$token')")
    }

    fun detachSubItem(input: ItemInput, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_detach_components"))
        append("('${Json.encodeToString(input)}',")
        append("'$token')")
    }

    fun category(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_retrieve_category_dataloader"))
        append("('$id',")
        append("'$token')")
    }

    fun operations(id: UUID, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_retrieve_operations_dataloader"))
        append("('$id',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    fun components(id: UUID, first: Int, after: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_retrieve_components_dataloader"))
        append("('$id',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("'$token')")
    }

    override fun count(token: UUID): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<ItemDao>())
        append("('$token')")
    }

}
