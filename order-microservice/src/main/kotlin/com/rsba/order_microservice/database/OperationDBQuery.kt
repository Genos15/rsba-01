package com.rsba.order_microservice.database

import java.util.*


object OperationDBQuery {

    fun onRetrieveGroupsByOperationId(operationId: UUID) =
        "SELECT on_retrieve_departments_by_operation_id('$operationId')"
}
