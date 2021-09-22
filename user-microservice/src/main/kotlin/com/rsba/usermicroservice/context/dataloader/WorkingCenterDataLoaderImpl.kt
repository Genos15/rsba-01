package com.rsba.usermicroservice.context.dataloader

import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.repository.WorkingCenterRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class WorkingCenterDataLoaderImpl(private val logger: KLogger, private val service: WorkingCenterRepository) {

    fun dataLoaderUserInWorkingCenter(workingCenterId: UUID): DataLoader<UUID, List<User>> {
        logger.warn { "+WorkingCenterDataLoaderImpl -> dataLoaderUserInWorkingCenter" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.retrieveUserOfWorkingCenter(
                    ids = ids,
                    userId = workingCenterId,
                    first = 1000,
                    after = UUID.randomUUID()
                )
            }
        }
    }

    fun dataLoaderManagersInWorkingCenter(instanceId: UUID): DataLoader<UUID, List<User>> {
        logger.warn { "+WorkingCenterDataLoaderImpl -> dataLoaderManagersInWorkingCenter" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.retrieveManagersOfWorkingCenter(
                    ids = ids,
                    userId = instanceId,
                    first = 1000,
                    after = UUID.randomUUID()
                )
            }
        }
    }
}