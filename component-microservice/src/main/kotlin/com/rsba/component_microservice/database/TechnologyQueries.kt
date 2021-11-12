package  com.rsba.component_microservice.database


import com.rsba.component_microservice.domain.input.TechnologyInput
import com.rsba.component_microservice.domain.input.TechnologyAndOperation
import com.rsba.component_microservice.domain.model.TechnologyFromOld
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object TechnologyQueries {

    fun createOrEdit(input: TechnologyInput, token: UUID) =
        "SELECT on_create_or_edit_technology('${Json.encodeToString(input)}', '$token')"

    fun addOrReorderOperation(input: TechnologyAndOperation, token: UUID) =
        "SELECT on_add_or_reorder_operation_in_technology('${Json.encodeToString(input)}', '$token')"

    fun unpinOperation(input: TechnologyAndOperation, token: UUID) =
        "SELECT on_unpin_operation_in_technology('${Json.encodeToString(input)}', '$token')"

    fun delete(input: UUID, token: UUID) =
        "SELECT on_delete_technology('$input', '$token')"

    fun retrieveById(input: UUID, token: UUID) =
        "SELECT on_retrieve_technology_by_id('$input', '$token')"

    fun retrieve(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_technologies('$first', ${after?.let { "'$it'" }},'$token')"

    fun search(content: String, token: UUID) =
        "SELECT on_search_technologies('$content','$token')"

    fun myOperations(technologyId: UUID) =
        "SELECT on_retrieve_operation_by_technology_id('$technologyId')"

    fun addOldTechnology(input: TechnologyFromOld, token: UUID?) =
        "SELECT on__add__old__technology('${Json.encodeToString(input)}', '$token')"
}
