package com.rsba.tasks_microservice.data.service.usecase.gantt

import com.rsba.tasks_microservice.data.dao.GanttDataDao
import com.rsba.tasks_microservice.data.service.usecase.queries.GanttQueries
import com.rsba.tasks_microservice.domain.model.GanttData
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.common.custom.gantt.RetrieveGanttDataUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "retrieve_gantt_data")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveUseCaseImpl(private val database: DatabaseClient) : RetrieveGanttDataUseCase {

    override suspend fun invoke(id: UUID, token: UUID): List<GanttData> =
        database.sql(GanttQueries.find(id = id, token = token))
            .map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? GanttDataDao?)?.to } ?: emptyList() }
            .onErrorResume {
                throw it
            }.awaitFirstOrElse { emptyList() }
}