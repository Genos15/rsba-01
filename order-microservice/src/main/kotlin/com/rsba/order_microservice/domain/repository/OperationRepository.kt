package com.rsba.order_microservice.domain.repository

import com.rsba.order_microservice.domain.model.Group
import java.util.*

interface OperationRepository {

    suspend fun myGroups(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Group>>
}