package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.TaskRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class WorkCenterDataLoaderImpl(private val logger: KLogger, private val service: TaskRepository) {

    fun dataLoaderUserInWorkingCenterWorkingInTask(userId: UUID): DataLoader<WorkingCenter, List<User>> {
        logger.warn { "+ItemDataLoaderImpl -> dataLoaderUserInWorkingCenterWorkingInTask" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myUsers(
                    ids = ids,
                    userId = userId,
                    first = 0,
                    after = UUID.randomUUID()
                )
            }
        }
    }


}