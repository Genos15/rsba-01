package com.rsba.component_microservice.data.context.dataloader

import com.rsba.component_microservice.domain.model.Group
import com.rsba.component_microservice.domain.repository.OperationRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class OperationDataLoaderImpl(private val service: OperationRepository) {
    fun dataLoaderDepartmentAndOperation(userId: UUID): DataLoader<UUID, List<Group>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.departments(ids = ids)
            }
        }

}