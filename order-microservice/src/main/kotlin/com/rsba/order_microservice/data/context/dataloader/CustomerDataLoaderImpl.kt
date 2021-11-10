package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.Customer
import com.rsba.order_microservice.domain.repository.CustomerRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomerDataLoaderImpl(private val logger: KLogger, private val service: CustomerRepository) {

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