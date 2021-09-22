package  com.rsba.order_microservice.database

import com.rsba.order_microservice.domain.input.AddEntityToCustomerInput
import com.rsba.order_microservice.domain.input.CreateOrEditCustomerInput
import com.rsba.order_microservice.domain.input.RemoveEntityOfCustomerInput
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomerDatabaseQuery {

    fun onCreateOrEditCustomer(input: CreateOrEditCustomerInput, token: UUID) =
        "SELECT on_create_or_edit_customer('${Json.encodeToString(input)}', '$token')"

    fun onAddEntityToCustomer(input: AddEntityToCustomerInput, token: UUID) =
        "SELECT on_add_entity_to_customer('${Json.encodeToString(input)}', '$token')"

    fun onDeleteCustomer(input: UUID, token: UUID) =
        "SELECT on_delete_customer('$input', '$token')"

    fun onRetrieveAllCustomer(first: Int, after: UUID?,token: UUID) =
        "SELECT on_retrieve_customers('$first', ${after?.let { "'$it'" }},'$token')"

    fun onRemoveEntityOfCustomer(input: RemoveEntityOfCustomerInput, token: UUID) =
        "SELECT on_remove_entity_of_customer('${Json.encodeToString(input)}', '$token')"

    fun onRetrieveCustomerOfOrder(input: UUID) =
        "SELECT on_retrieve_customer_by_order_id('$input', '')"

    fun onRetrieveEntitiesOfCustomer(input: UUID) =
        "SELECT on_retrieve_entity_by_customer_id('$input', '')"

    fun onRetrieveOneCustomer(input: UUID, token: UUID) =
        "SELECT on_retrieve_customer_by_id('$input', '$token')"
}
