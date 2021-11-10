package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.WorklogRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class WorklogDataLoaderImpl(private val logger: KLogger, private val service: WorklogRepository) {

    fun dataLoaderActorInWorklog(userId: UUID): DataLoader<UUID, Optional<User>> {
        logger.warn { "+WorklogDataLoaderImpl -> dataLoaderActorInWorklog" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myActor(
                    ids = ids,
                    userId = userId,
                    first = 0,
                    after = UUID.randomUUID()
                )
            }
        }
    }


}