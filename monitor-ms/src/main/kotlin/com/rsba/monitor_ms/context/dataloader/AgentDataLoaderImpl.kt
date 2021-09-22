package com.rsba.monitor_ms.context.dataloader

import com.rsba.monitor_ms.domain.model.Agent
import com.rsba.monitor_ms.repository.OrderRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class AgentDataLoaderImpl(private val logger: KLogger, private val service: OrderRepository) {

    fun dataLoaderAgentOfUser(userId: UUID): DataLoader<UUID, Agent?> {
        logger.warn { "+--- AgentDataLoaderImpl -> dataLoaderAgentOfUser" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.retrieveAgentOfOrder(
                    orderIds = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }

    fun dataLoaderManagerOfOrder(userId: UUID): DataLoader<UUID, Agent?> {
        logger.warn { "+--- AgentDataLoaderImpl -> dataLoaderManagerOfOrder" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.retrieveManagerOfOrder(
                    orderIds = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }
}