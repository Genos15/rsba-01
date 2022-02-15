package com.rsba.tasks_microservice.domain.repository

import com.rsba.tasks_microservice.domain.model.*
import java.util.*

interface GanttRepository {
    suspend fun retrieve(id: UUID, token: UUID): List<GanttData>
}