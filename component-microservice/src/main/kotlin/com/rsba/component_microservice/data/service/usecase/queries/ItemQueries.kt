package  com.rsba.component_microservice.data.service.usecase.queries

import com.rsba.component_microservice.data.dao.ItemDao
import com.rsba.component_microservice.domain.queries.IBaseQuery
import com.rsba.component_microservice.domain.format.JsonHandlerKotlin
import com.rsba.component_microservice.domain.queries.QueryBuilder
import com.rsba.component_microservice.domain.input.ItemInput
import com.rsba.component_microservice.domain.model.Edition
import com.rsba.component_microservice.domain.model.MutationAction
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.time.OffsetDateTime
import java.util.*

@ExperimentalSerializationApi
object ItemQueries : IBaseQuery<ItemInput, ItemDao> {

    override fun createOrEdit(
        input: ItemInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?
    ): String =
        buildString {
            append(QueryBuilder.CreateOrEdit.buildRequestDef<ItemDao>())
            append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
            append("${action?.let { "'$it'" }},")
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
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
        append("'$token')")
    }

    fun detachOperation(input: ItemInput, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_detach_operations"))
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
        append("'$token')")
    }

    fun attachSubItem(input: ItemInput, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_attach_components"))
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
        append("'$token')")
    }

    fun detachSubItem(input: ItemInput, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_detach_components"))
        append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
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

    fun usages(
        first: Int,
        after: UUID?,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_retrieve_usages"))
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("${from?.let { "'$it'" }},")
        append("${to?.let { "'$it'" }},")
        append("'$token')")
    }

    fun usages(
        input: String,
        first: Int,
        after: UUID?,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_search_usages"))
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("${from?.let { "'$it'" }},")
        append("${to?.let { "'$it'" }},")
        append("'$token')")
    }

    fun usage(
        input: UUID,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<ItemDao>(customQuery = "_on_find_usage"))
        append("('$input',")
        append("${from?.let { "'$it'" }},")
        append("${to?.let { "'$it'" }},")
        append("'$token')")
    }

}
