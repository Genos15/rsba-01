package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.Customer
import com.rsba.order_microservice.domain.repository.CustomerRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomerDataLoaderImpl(private val service: CustomerRepository) {
    fun entitiesLoader(userId: UUID): DataLoader<UUID, List<Customer>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.entities(ids = ids)
            }
        }
}