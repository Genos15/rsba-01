package  com.rsba.component_microservice.domain.queries

import com.rsba.component_microservice.domain.format.ModelType
import com.rsba.component_microservice.domain.format.ModelTypeCase
import kotlin.reflect.full.findAnnotation

sealed class QueryBuilder(val query: String? = null) {

    object CreateOrEdit : QueryBuilder(query = "_on_create_or_edit")
    object Delete : QueryBuilder(query = "_on_delete")
    object Find : QueryBuilder(query = "_on_find")
    object Count : QueryBuilder(query = "_on_count")
    object Retrieve : QueryBuilder(query = "_on_retrieve")
    object Search : QueryBuilder(query = "_on_search")
    object Custom : QueryBuilder()

    inline fun <reified T> buildRequestDef(
        customQuery: String? = null,
        forceMapping: ModelTypeCase? = null
    ): String {
        val myClass = T::class
        val annotation = myClass.findAnnotation<ModelType>()
        return if (annotation != null) {
            "select ${annotation._class}${query ?: customQuery ?: ""}"
        } else {
            "select null;"
        }
    }
}
