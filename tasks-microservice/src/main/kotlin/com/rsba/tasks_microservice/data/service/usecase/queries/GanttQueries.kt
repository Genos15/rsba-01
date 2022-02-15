package  com.rsba.tasks_microservice.data.service.usecase.queries

import com.rsba.tasks_microservice.data.dao.GanttDataDao
import com.rsba.tasks_microservice.domain.queries.QueryBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.*

@ExperimentalSerializationApi
object GanttQueries {

    fun find(id: UUID, token: UUID): String = buildString {
        append(QueryBuilder.Find.buildRequestDef<GanttDataDao>())
        append("(${id.let { "'$it'" }},")
        append("'$token')")
    }

}
