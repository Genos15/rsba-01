package com.rsba.component_microservice.data.context.dataloader

import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.repository.TechnologyRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class TechnologyDataLoaderImpl(private val service: TechnologyRepository) {
    fun dataLoaderOperationInTechnology(userId: UUID): DataLoader<UUID, List<Operation>> {
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.operations(ids = ids)
            }
        }
    }

}