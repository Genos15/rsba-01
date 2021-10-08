package  com.rsba.order_microservice.database


import com.rsba.order_microservice.domain.input.ElkGraphInput
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object GraphItemDBQueries {

    fun retrieveElkItems(input: ElkGraphInput, token: UUID) =
        "SELECT on_retrieve_item_dependencies('${Json.encodeToString(input)}', '$token')"

}
