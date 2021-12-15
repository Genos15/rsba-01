package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.ItemRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemDataLoaderImpl(private val service: ItemRepository) {

    fun statisticsLoader(userId: UUID): DataLoader<UUID, Optional<ItemStatistics>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.statistics(ids = ids) }
        }

    fun whoAddedLoader(userId: UUID): DataLoader<UUID, Optional<User>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.whoAdded(ids = ids) }
        }

    fun technologiesLoader(userId: UUID): DataLoader<UUID, List<Technology>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.technologies(ids = ids) }
        }

    fun parametersLoader(userId: UUID): DataLoader<UUID, List<Parameter>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.parameters(ids = ids)
            }
        }

}