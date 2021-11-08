package com.rsba.component_microservice.domain.usecase.custom.item

import com.rsba.component_microservice.domain.model.Item
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveSubItemDataLoaderUseCase {
    suspend operator fun invoke(database: DatabaseClient, ids: Set<UUID>, token: UUID): Map<UUID, List<Item>>
}