package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.OrderRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderDataLoaderImpl(private val service: OrderRepository) {

    fun customerLoader(userId: UUID): DataLoader<UUID, Optional<Customer>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.customer(ids = ids)
            }
        }

    fun typeLoader(userId: UUID): DataLoader<UUID, Optional<OrderType>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.type(ids = ids)
            }
        }

    fun managerLoader(userId: UUID): DataLoader<UUID, Optional<Agent>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.manager(ids = ids)
            }
        }

    fun agentLoader(userId: UUID): DataLoader<UUID, Optional<Agent>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.agent(ids = ids)
            }
        }

    fun itemsLoader(userId: UUID): DataLoader<UUID, List<Item>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.items(ids = ids)
            }
        }

    fun tasksLoader(userId: UUID): DataLoader<UUID, List<Task>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.tasks(ids = ids)
            }
        }

    fun technologiesLoader(userId: UUID): DataLoader<UUID, List<Technology>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.technologies(ids = ids)
            }
        }

    fun parametersLoader(userId: UUID): DataLoader<UUID, List<Parameter>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.parameters(ids = ids)
            }
        }

    fun categoriesLoader(userId: UUID): DataLoader<UUID, List<ItemCategory>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.categories(ids = ids)
            }
        }

    fun worklogsLoader(userId: UUID): DataLoader<UUID, List<Worklog>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.worklogs(ids = ids)
            }
        }

}