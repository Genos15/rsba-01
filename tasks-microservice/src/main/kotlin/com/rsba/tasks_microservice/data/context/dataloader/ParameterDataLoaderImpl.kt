package com.rsba.tasks_microservice.data.context.dataloader

import com.rsba.tasks_microservice.domain.repository.TaskRepository
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.future.future
//import org.dataloader.DataLoader
import org.springframework.stereotype.Component

//import java.util.*

@Component
class ParameterDataLoaderImpl(private val service: TaskRepository) {

//    fun dataLoaderValues(userId: UUID): DataLoader<UUID, List<String>> {
//        return DataLoader.newMappedDataLoader { ids ->
//            GlobalScope.future { service.values(ids = ids) }
//        }
//    }

}