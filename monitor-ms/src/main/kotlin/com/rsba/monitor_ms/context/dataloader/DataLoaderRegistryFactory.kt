package  com.rsba.monitor_ms.context.dataloader

import mu.KLogger
import org.dataloader.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoaderRegistryFactory(
    private val logger: KLogger,
    private val forCustomer: CustomerDataLoaderImpl,
    private val forAgent: AgentDataLoaderImpl,
    private val forCategory: CategoryOfItemDataLoaderImpl,
) {

    companion object {
        const val CUSTOMERS_IN_CUSTOMER = "CUSTOMERS_IN_CUSTOMER"
        const val CUSTOMER_OF_ORDER = "CUSTOMER_OF_ORDER"

        const val AGENT_OF_ORDER = "AGENT_OF_ORDER"
        const val MANAGER_OF_ORDER = "MANAGER_OF_ORDER"

        const val CATEGORY_OF_ITEM_IN_ORDER = "CATEGORY_OF_ITEM_IN_ORDER"
    }

    fun create(instanceId: UUID): DataLoaderRegistry {
        logger.warn { "+--- DataLoaderRegistryFactory -> create" }
        val registry = DataLoaderRegistry()
        logger.warn { "create = $instanceId" }

        registry.register(CUSTOMERS_IN_CUSTOMER, forCustomer.dataLoaderEntitiesOfCustomer(userId = instanceId))
        registry.register(CUSTOMER_OF_ORDER, forCustomer.dataLoaderCustomerOfOrder(userId = instanceId))
        registry.register(AGENT_OF_ORDER, forAgent.dataLoaderAgentOfUser(userId = instanceId))
        registry.register(MANAGER_OF_ORDER, forAgent.dataLoaderManagerOfOrder(userId = instanceId))
        registry.register(CATEGORY_OF_ITEM_IN_ORDER, forCategory.dataLoaderCategoriesOfItemInOrder(userId = instanceId))

        return registry
    }


}

