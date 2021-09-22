package com.rsba.usermicroservice.context.dataloader

import com.rsba.usermicroservice.domain.model.ContactInfo
import com.rsba.usermicroservice.repository.ContactInfoRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class ContactInfoDataLoaderImpl(private val logger: KLogger, private val service: ContactInfoRepository) {

    fun dataLoaderContactsOfUser(userId: UUID): DataLoader<UUID, List<ContactInfo>> {
        logger.warn { "+--- ContactInfoDataLoaderImpl -> dataLoaderContactsOfUser" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.onRetrieveByUserId(
                    userIds = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }
}