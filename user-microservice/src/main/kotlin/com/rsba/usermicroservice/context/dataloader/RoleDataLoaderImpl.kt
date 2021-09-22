package com.rsba.usermicroservice.context.dataloader

import com.rsba.usermicroservice.domain.model.Role
import com.rsba.usermicroservice.repository.RolesRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoleDataLoaderImpl(private val logger: KLogger, private val service: RolesRepository) {

    fun dataLoaderRoleOfUser(userId: UUID): DataLoader<UUID, Role?> {
        logger.warn { "+--- RoleDataLoaderImpl -> dataLoaderRoleOfUser" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.onRetrieveByUserId(
                    userIds = ids,
                    userId = userId,
                    first = 1000,
                    after = UUID.randomUUID()
                )
            }
        }
    }
}