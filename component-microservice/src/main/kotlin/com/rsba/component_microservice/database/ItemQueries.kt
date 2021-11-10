package  com.rsba.component_microservice.database


import com.rsba.component_microservice.domain.model.ItemFromOld
import com.rsba.component_microservice.domain.model.ItemTechnology
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object ItemQueries {

    fun importTechnology(input: ItemTechnology, token: UUID) =
        "SELECT on_import_technology_in_item('${Json.encodeToString(input)}', '$token')"


    fun itemsByCategoryId(categoryId: UUID, first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_items_by_category_id('$categoryId', '$first', ${after?.let { "'$it'" }},'$token')"

    fun addOldItem(input: ItemFromOld, token: UUID?) =
        "SELECT on__add__old__item('${Json.encodeToString(input)}', '$token')"

    fun search(input: String, first: Int, after: UUID?, token: UUID) =
        "SELECT on_search_items('$input','$first', ${after?.let { "'$it'" }},'$token')"
}
