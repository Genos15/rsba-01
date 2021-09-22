package com.rsba.usermicroservice.context.dataloader

import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.domain.model.WorkingCenter
import com.rsba.usermicroservice.repository.GroupsRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class GroupDataLoaderImpl(private val logger: KLogger, private val service: GroupsRepository) {

    fun dataLoaderWorkingCenterInGroup(groupId: UUID): DataLoader<UUID, List<WorkingCenter>> {
        logger.warn { "+GroupDataLoaderImpl -> dataLoaderWorkingCenterInGroup" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.retrieveWorkingCenterInGroup(
                    ids = ids,
                    userId = groupId,
                    first = 1000,
                    after = UUID.randomUUID()
                )
            }
        }
    }

    fun dataLoaderManagersInGroup(instanceId: UUID): DataLoader<UUID, List<User>> {
        logger.warn { "+GroupDataLoaderImpl -> dataLoaderManagersInGroup" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.retrieveManagersInGroup(
                    ids = ids,
                    userId = instanceId,
                    first = 1000,
                    after = UUID.randomUUID()
                )
            }
        }
    }
}