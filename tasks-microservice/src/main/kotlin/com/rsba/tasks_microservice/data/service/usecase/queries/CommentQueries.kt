package  com.rsba.tasks_microservice.data.service.usecase.queries

import com.rsba.tasks_microservice.data.dao.CommentDao
import com.rsba.tasks_microservice.domain.queries.IBaseQuery
import com.rsba.tasks_microservice.domain.format.JsonHandlerKotlin
import com.rsba.tasks_microservice.domain.input.CommentInput
import com.rsba.tasks_microservice.domain.queries.QueryBuilder
import com.rsba.tasks_microservice.domain.model.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import java.time.OffsetDateTime
import java.util.*

@ExperimentalSerializationApi
object CommentQueries : IBaseQuery<CommentInput, CommentDao> {

    override fun createOrEdit(input: CommentInput, token: UUID, action: MutationAction?, case: Edition?): String =
        buildString {
            append(QueryBuilder.CreateOrEdit.buildRequestDef<CommentDao>())
            append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
            append("${action?.let { "'$it'" }},")
            append("'$token')")
        }


    fun createOrEdit2(input: CommentInput, token: UUID, layer: CommentLayer?): String =
        buildString {
            append(QueryBuilder.CreateOrEdit.buildRequestDef<CommentDao>())
            append("('${JsonHandlerKotlin.handler.encodeToString(input)}',")
            append("${layer?.let { "'$it'" }},")
            append("'$token')")
        }

    override fun delete(input: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Delete.buildRequestDef<CommentDao>())
        append("('$input',")
        append("'$token')")
    }

    override fun retrieve(
        first: Int,
        after: UUID?,
        status: TaskStatus?,
        layer: TaskLayer?,
        id: UUID?,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Retrieve.buildRequestDef<CommentDao>())
        append("($first,")
        append("${after?.let { "'$it'" }},")
        append("${status?.let { "'$it'" }},")
        append("${layer?.let { "'$it'" }},")
        append("${id?.let { "'$it'" }},")
        append("${rangeStart?.let { "'$rangeStart'" }},")
        append("${rangeEnd?.let { "'$rangeEnd'" }},")
        append("'$token')")
    }

    override fun search(
        input: String,
        first: Int,
        after: UUID?,
        status: TaskStatus?,
        layer: TaskLayer?,
        id: UUID?,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?,
        token: UUID
    ): String = buildString {
        append(QueryBuilder.Search.buildRequestDef<CommentDao>())
        append("('$input',")
        append("$first,")
        append("${after?.let { "'$it'" }},")
        append("${status?.let { "'$it'" }},")
        append("${layer?.let { "'$it'" }},")
        append("${id?.let { "'$it'" }},")
        append("${rangeStart?.let { "'$rangeStart'" }},")
        append("${rangeEnd?.let { "'$rangeEnd'" }},")
        append("'$token')")
    }

    override fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<CommentDao>())
        append("('$id',")
        append("'$token')")
    }

    override fun count(status: TaskStatus?, layer: TaskLayer?, id: UUID?, token: UUID): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<CommentDao>())
        append("(${status?.let { "'$it'" }},")
        append("${layer?.let { "'$it'" }},")
        append("${id?.let { "'$it'" }},")
        append("'$token')")
    }

    fun reporter(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Custom.buildRequestDef<CommentDao>(customQuery = "_on_find_reporter"))
        append("('$id',")
        append("'$token')")
    }

    fun users(id: UUID, first: Int, after: UUID? = null, token: UUID): String =
        buildString {
            append(QueryBuilder.Custom.buildRequestDef<CommentDao>(customQuery = "_on_retrieve_users"))
            append("('$id',")
            append("$first,")
            append("${after?.let { "'$it'" }},")
            append("'$token')")
        }

    fun count2(hostId: UUID, layer: CommentLayer?, token: UUID): String = buildString {
        append(QueryBuilder.Count.buildRequestDef<CommentDao>())
        append("(${hostId.let { "'$it'" }},")
        append("${layer?.let { "'$it'" }},")
        append("'$token')")
    }


}
