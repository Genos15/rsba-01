package com.rsba.tasks_microservice.data.context.dataloader

import com.rsba.tasks_microservice.domain.model.*
import com.rsba.tasks_microservice.domain.repository.CommentRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class CommentDataLoaderImpl(private val service: CommentRepository) {

    fun userLoader(userId: UUID): DataLoader<UUID, Optional<User>> {
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.user(ids = ids) }
        }
    }

}