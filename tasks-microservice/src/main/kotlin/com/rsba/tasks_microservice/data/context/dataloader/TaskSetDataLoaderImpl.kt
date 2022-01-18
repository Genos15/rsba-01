package com.rsba.tasks_microservice.data.context.dataloader

import com.rsba.tasks_microservice.domain.model.*
import com.rsba.tasks_microservice.domain.repository.TaskSetRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskSetDataLoaderImpl(private val service: TaskSetRepository) {

    fun usersLoader(userId: UUID): DataLoader<UUID, List<User>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.users(ids = ids)
            }
        }

    fun tasksLoader(userId: UUID): DataLoader<UUID, List<Task>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.tasks(ids = ids)
            }
        }

    fun commentsLoader(userId: UUID): DataLoader<UUID, List<Comment>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.comments(ids = ids)
            }
        }

}