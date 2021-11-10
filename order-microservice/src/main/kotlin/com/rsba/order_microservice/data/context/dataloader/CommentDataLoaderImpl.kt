package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.User
import com.rsba.order_microservice.domain.repository.CommentRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class CommentDataLoaderImpl(private val logger: KLogger, private val service: CommentRepository) {

    fun dataLoaderActorOfComment(userId: UUID): DataLoader<UUID, Optional<User>> {
        logger.warn { "+CommentDataLoaderImpl -> dataLoaderActorOfComment" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myActor(
                    ids = ids,
                    userId = userId,
                    first = 0,
                    after = null
                )
            }
        }
    }

}