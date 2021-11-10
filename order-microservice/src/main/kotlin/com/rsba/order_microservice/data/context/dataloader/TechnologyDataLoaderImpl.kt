package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.Operation
import com.rsba.order_microservice.domain.repository.TechnologyRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class TechnologyDataLoaderImpl(private val logger: KLogger, private val service: TechnologyRepository) {
    fun dataLoaderOperationInTechnology(userId: UUID): DataLoader<UUID, List<Operation>> {
        logger.warn { "+TechnologyDataLoaderImpl->dataLoaderOperationInTechnology" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.myOperations(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }

}