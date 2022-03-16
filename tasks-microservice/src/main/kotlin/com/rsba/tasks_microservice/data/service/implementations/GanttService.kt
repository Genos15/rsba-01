package com.rsba.tasks_microservice.data.service.implementations

import com.rsba.tasks_microservice.domain.model.*
import com.rsba.tasks_microservice.domain.repository.GanttRepository
import com.rsba.tasks_microservice.domain.usecase.common.custom.gantt.RetrieveGanttDataUseCase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*

@Service
class GanttService(
    @Qualifier("retrieve_gantt_data") private val retrieveGanttDataUseCase: RetrieveGanttDataUseCase,
) : GanttRepository {
    override suspend fun retrieve(id: UUID, token: UUID): List<GanttData> =
        retrieveGanttDataUseCase(id = id, token = token)
}