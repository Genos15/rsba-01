package  com.rsba.component_microservice.database


  import com.rsba.component_microservice.domain.model.OperationFromOld
 import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object OperationQueries {
    fun addOldOperation(input: OperationFromOld, token: UUID?) =  "SELECT on__add__old__operation('${Json.encodeToString(input)}', '$token')"
}
