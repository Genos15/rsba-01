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
class UserDataLoaderImpl(private val logger: KLogger, private val service: TaskRepository) {

    fun dataLoaderPersonalInfoOfUser(userId: UUID): DataLoader<UUID, PersonalInfo> {
        logger.warn { "+UserDataLoaderImpl -> dataLoaderPersonalInfoOfUser" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myPersonalInfo(
                    ids = ids,
                    userId = userId,
                    first = 0,
                    after = null
                )
            }
        }
    }

    fun dataLoaderContactInfoOfUser(userId: UUID): DataLoader<UUID, List<ContactInfo>> {
        logger.warn { "+UserDataLoaderImpl -> dataLoaderContactInfoOfUser" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myContactInfo(
                    ids = ids,
                    userId = userId,
                    first = 0,
                    after = null
                )
            }
        }
    }


}