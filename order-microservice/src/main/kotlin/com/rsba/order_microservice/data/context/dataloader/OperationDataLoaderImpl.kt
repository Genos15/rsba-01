package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.Group
import com.rsba.order_microservice.domain.repository.OperationRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class OperationDataLoaderImpl(private val logger: KLogger, private val service: OperationRepository) {
    fun dataLoaderGroupInOperation(userId: UUID): DataLoader<UUID, List<Group>> {
        logger.warn { "+--- OperationDataLoaderImpl -> dataLoaderGroupInOperation" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myGroups(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }

}