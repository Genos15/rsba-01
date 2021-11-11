package com.rsba.component_microservice.database

import com.rsba.component_microservice.domain.input.OperationInput
import com.rsba.component_microservice.domain.input.OperationAndGroupInput
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.util.*

@Component
class OperationDBQuery {

    fun onCreateOrEditOperation(input: OperationInput, token: UUID) =
        "SELECT on_create_or_edit_operation('${Json.encodeToString(input)}', '$token')"

    fun onAttachOperationToDepartment(input: OperationAndGroupInput, token: UUID) =
        "SELECT on_attach_operation_with_group('${Json.encodeToString(input)}', '$token')"

    fun onDetachOperationToDepartment(input: OperationAndGroupInput, token: UUID) =
        "SELECT on_detach_operation_with_group('${Json.encodeToString(input)}', '$token')"

    fun onDeleteOperation(input: UUID, token: UUID) =
        "SELECT on_delete_operation('$input', '$token')"

    fun onRetrieveAllOperation(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_operations('$first', ${after?.let { "'$it'" }},'$token')"

    fun onRetrieveGroupsByOperationId(operationId: UUID) =
        "SELECT on_retrieve_departments_by_operation_id('$operationId')"
}
