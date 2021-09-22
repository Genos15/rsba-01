package com.rsba.monitor_ms.context.dataloader

import com.rsba.monitor_ms.domain.model.Customer
import com.rsba.monitor_ms.repository.CustomerRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerDataLoaderImpl(private val logger: KLogger, private val service: CustomerRepository) {

    @OptIn(DelicateCoroutinesApi::class)
    fun dataLoaderEntitiesOfCustomer(userId: UUID): DataLoader<UUID, List<Customer>> {
        logger.warn { "+--- CustomerDataLoaderImpl -> dataLoaderEntitiesOfCustomer" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.retrieveEntitiesOfCustomer(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }

    fun dataLoaderCustomerOfOrder(userId: UUID): DataLoader<UUID, Customer> {
        logger.warn { "+--- CustomerDataLoaderImpl -> dataLoaderCustomerOfOrder" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.retrieveCustomerOfOrder(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }
}