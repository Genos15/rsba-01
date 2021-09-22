package com.rsba.component_microservice.context.dataloader

import org.dataloader.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoaderRegistryFactory(
    private val _operation: OperationDataLoaderImpl,
    private val _item: ItemDataLoaderImpl,
    private val _technology: TechnologyDataLoaderImpl
) {

    companion object {
        const val GROUP_IN_OPERATION_DATALOADER = "GROUP_IN_OPERATION_DATALOADER"
        const val OPERATION_IN_ITEM_DATALOADER = "OPERATION_IN_ITEM_DATALOADER"
        const val CATEGORY_OF_ITEM_IN_ITEM_DATALOADER = "CATEGORY_OF_ITEM_IN_ITEM_DATALOADER"
        const val OPERATION_IN_TECHNOLOGY_DATALOADER = "OPERATION_IN_TECHNOLOGY_DATALOADER"
    }

    fun create(instanceId: UUID): DataLoaderRegistry {
        val registry = DataLoaderRegistry()
        registry.register(GROUP_IN_OPERATION_DATALOADER, _operation.dataLoaderGroupInOperation(userId = instanceId))
        registry.register(OPERATION_IN_ITEM_DATALOADER, _item.dataLoaderOperationInItem(userId = instanceId))
        registry.register(CATEGORY_OF_ITEM_IN_ITEM_DATALOADER, _item.dataLoaderCategoryInItem(userId = instanceId))
        registry.register(
            OPERATION_IN_TECHNOLOGY_DATALOADER,
            _technology.dataLoaderOperationInTechnology(userId = instanceId)
        )
        return registry
    }

}

