package com.rsba.order_microservice.data.service.implementation.tasks

import com.rsba.order_microservice.database.TaskDBHandler
import com.rsba.order_microservice.database.TaskDBQueries
import com.rsba.order_microservice.domain.model.Task
import com.rsba.order_microservice.domain.model.TaskLevel
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveTaskImpl {


    suspend fun fnTasksByDepartmentId(
        departmentId: UUID,
        first: Int,
        after: UUID?,
        token: UUID,
        level: TaskLevel?,
        database: DatabaseClient,
    ): List<Task> =
        database.sql(
            TaskDBQueries.retrieveTaskByDepartmentId(
                token = token,
                first = first,
                after = after,
                departmentId = departmentId,
                level = level
            )
        ).map { row -> TaskDBHandler.all(row = row) }
            .first()
            .onErrorResume {
                println { "tasksByDepartmentId = ${it.message}" }
                throw it
            }
            .log()
            .awaitFirst()


    suspend fun fnTasksByWorkingCenterId(
        workingCenterId: UUID,
        first: Int,
        after: UUID?,
        token: UUID,
        level: TaskLevel?,
        database: DatabaseClient,
    ): List<Task> =
        database.sql(
            TaskDBQueries.retrieveTaskByWorkingCenterId(
                token = token,
                first = first,
                after = after,
                workingCenterId = workingCenterId,
                level = level
            )
        ).map { row -> TaskDBHandler.all(row = row) }
            .first()
            .onErrorResume {
                println { "fnTasksByWorkingCenterId = ${it.message}" }
                throw it
            }
            .log()
            .awaitFirst()


    suspend fun fnTasksByUserToken(
        first: Int,
        after: UUID?,
        token: UUID,
        level: TaskLevel?,
        database: DatabaseClient,
    ): List<Task> =
        database.sql(
            TaskDBQueries.retrieveTaskByUserToken(
                token = token,
                first = first,
                after = after,
                level = level
            )
        ).map { row -> TaskDBHandler.all(row = row) }
            .first()
            .onErrorResume {
                println { "fnTasksByUserToken = ${it.message}" }
                throw it
            }
            .log()
            .awaitFirst()
}