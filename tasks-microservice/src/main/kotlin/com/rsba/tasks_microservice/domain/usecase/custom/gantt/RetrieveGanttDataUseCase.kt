package com.rsba.tasks_microservice.domain.usecase.common.custom.gantt

import com.rsba.tasks_microservice.domain.model.GanttData
import java.util.*

interface RetrieveGanttDataUseCase {
    suspend operator fun invoke(id: UUID, token: UUID): List<GanttData>
}