package com.rsba.order_microservice.data.context.dataloader

 import com.rsba.order_microservice.domain.repository.OrderRepository
import mu.KLogger
 import org.springframework.stereotype.Component

@Component
class AgentDataLoaderImpl(private val logger: KLogger, private val service: OrderRepository) {

//    fun dataLoaderAgentOfUser(userId: UUID): DataLoader<UUID, Agent?> {
//        logger.warn { "+--- AgentDataLoaderImpl -> dataLoaderAgentOfUser" }
//        return DataLoader.newMappedDataLoader { ids, env ->
//            logger.warn { env }
//            GlobalScope.future {
//                service.retrieveAgentOfOrder(
//                    orderIds = ids,
//                    userId = userId,
//                    page = 0,
//                    size = 1000
//                )
//            }
//        }
//    }
//
//    fun dataLoaderManagerOfOrder(userId: UUID): DataLoader<UUID, Agent?> {
//        logger.warn { "+--- AgentDataLoaderImpl -> dataLoaderManagerOfOrder" }
//        return DataLoader.newMappedDataLoader { ids, env ->
//            logger.warn { env }
//            GlobalScope.future {
//                service.retrieveManagerOfOrder(
//                    orderIds = ids,
//                    userId = userId,
//                    page = 0,
//                    size = 1000
//                )
//            }
//        }
//    }
}