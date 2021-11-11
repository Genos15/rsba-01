package com.rsba.component_microservice.data.context.dataloader

import org.dataloader.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoaderRegistryFactory(
    private val _operation: OperationDataLoaderImpl,
    private val _item: ItemDataLoaderImpl,
    private val _technology: TechnologyDataLoaderImpl,
    private val _item_category: ItemCategoryDataLoaderImpl
) {

    companion object {
        const val GROUP_IN_OPERATION_DATALOADER = "GROUP_IN_OPERATION_DATALOADER"
        const val OPERATION_IN_ITEM_DATALOADER = "OPERATION_IN_ITEM_DATALOADER"
        const val CATEGORY_OF_ITEM_IN_ITEM_DATALOADER = "CATEGORY_OF_ITEM_IN_ITEM_DATALOADER"
        const val OPERATION_IN_TECHNOLOGY_DATALOADER = "OPERATION_IN_TECHNOLOGY_DATALOADER"
        const val SUB_ITEM_CATEGORY_DATALOADER = "SUB_ITEM_CATEGORY_DATALOADER"
        const val ITEMS_COMPONENTS_DATALOADER = "ITEMS_COMPONENTS_DATALOADER"
        const val ITEMS_CATEGORY_DATALOADER = "ITEMS_CATEGORY_DATALOADER"
        const val SUB_ITEMS_IN_ITEM_CATEGORY_DATALOADER = "SUB_ITEMS_IN_ITEM_CATEGORY_DATALOADER"

    }

    fun create(instanceId: UUID): DataLoaderRegistry {
        val registry = DataLoaderRegistry()
        registry.register(
            GROUP_IN_OPERATION_DATALOADER,
            _operation.dataLoaderDepartmentAndOperation(userId = instanceId)
        )
        registry.register(OPERATION_IN_ITEM_DATALOADER, _item.dataLoaderOperation(userId = instanceId))
        registry.register(ITEMS_COMPONENTS_DATALOADER, _item.dataLoaderComponents(userId = instanceId))
        registry.register(ITEMS_CATEGORY_DATALOADER, _item.dataLoaderCategory(userId = instanceId))
        registry.register(
            OPERATION_IN_TECHNOLOGY_DATALOADER,
            _technology.dataLoaderOperationInTechnology(userId = instanceId)
        )
        registry.register(SUB_ITEM_CATEGORY_DATALOADER, _item_category.dataLoaderSubItemCategory(userId = instanceId))
        registry.register(SUB_ITEMS_IN_ITEM_CATEGORY_DATALOADER, _item_category.dataLoaderSubItem(userId = instanceId))
        return registry
    }

}

