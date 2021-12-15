package  com.rsba.order_microservice.data.context.dataloader

import org.dataloader.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoaderRegistryFactory(
    private val _customer: CustomerDataLoaderImpl,
//    private val forCategory: CategoryOfItemDataLoaderImpl,
    private val _order: OrderDataLoaderImpl,
    private val _item: ItemDataLoaderImpl,
    private val forOperation: OperationDataLoaderImpl,
    private val _task: TaskDataLoaderImpl,
    private val forUser: UserDataLoaderImpl,
    private val forComment: CommentDataLoaderImpl,
    private val forDepartment: DepartmentDataLoaderImpl,
    private val forWorkcenter: WorkCenterDataLoaderImpl,
    private val forWorklog: WorklogDataLoaderImpl,
    private val _technology: TechnologyDataLoaderImpl,
    private val _parameter: ParameterDataLoaderImpl
) {

    companion object {

        //        const val CATEGORY_OF_ITEM_IN_ORDER_POST_MVP = "CATEGORY_OF_ITEM_IN_ORDER_POST_MVP"
        const val ITEM_IN_ORDER = "ITEM_IN_ORDER"
        const val OPERATIONS_IN_ITEM = "OPERATIONS_IN_ITEM"
        const val CATEGORY_IN_ITEM = "CATEGORY_IN_ITEM"
        const val GROUP_IN_OPERATION_DATALOADER = "GROUP_IN_OPERATION_DATALOADER"
        const val TASK_IN_ITEM_DATALOADER = "TASK_IN_ITEM_DATALOADER"


//        const val OPERATION_IN_TASK_DATALOADER = "OPERATION_IN_TASK_DATALOADER"
//        const val DEPARTMENTS_IN_TASK_DATALOADER = "DEPARTMENTS_IN_TASK_DATALOADER"
//        const val COMMENTS_IN_TASK_DATALOADER = "COMMENTS_IN_TASK_DATALOADER"
//        const val ITEM_IN_TASK_DATALOADER = "ITEM_IN_TASK_DATALOADER"
//
//        const val ORDER_IN_TASK_DATALOADER = "ORDER_IN_TASK_DATALOADER"

        const val PERSONAL_INFO_OF_USER_DATALOADER = "PERSONAL_INFO_OF_USER_DATALOADER"
        const val CONTACT_INFO_OF_USER_DATALOADER = "CONTACT_INFO_OF_USER_DATALOADER"

        const val ACTOR_OF_COMMENT_DATALOADER = "ACTOR_OF_COMMENT_DATALOADER"

        const val WORKCENTER_OF_DEPARTMENT_IN_TASK_DATALOADER = "WORKCENTER_OF_DEPARTMENT_IN_TASK_DATALOADER"
        const val USER_IN_WORK_CENTER_WORKING_IN_TASK_DATALOADER = "USER_IN_WORK_CENTER_WORKING_IN_TASK_DATALOADER"
//        const val USER_IN_TASK_DATALOADER = "USER_IN_TASK_DATALOADER"

        const val ACTOR_IN_WORKLOG_DATALOADER = "ACTOR_IN_WORKLOG_DATALOADER"
        const val OPERATION_IN_TECHNOLOGY_DATALOADER = "OPERATION_IN_TECHNOLOGY_DATALOADER"

        const val ACTOR_IN_DETAIL_OF_ORDER_DATALOADER = "ACTOR_IN_DETAIL_OF_ORDER_DATALOADER"

        const val TECHNOLOGIES_IN_DETAIL_OF_ORDER_DATALOADER = "TECHNOLOGIES_IN_DETAIL_OF_ORDER_DATALOADER"

        const val ITEM_IN_ITEM_DATALOADER = "ITEM_IN_ITEM_DATALOADER"

        const val POTENTIAL_VALUES_PARAMETER_DATALOADER = "POTENTIAL_VALUES_PARAMETER_DATALOADER"

        // order references
        const val LOADER_FACTORY_CUSTOMER_OF_ORDER = "CUSTOMER_OF_ORDER"
        const val LOADER_FACTORY_MANAGER_OF_ORDER = "MANAGER_OF_ORDER"
        const val LOADER_FACTORY_AGENT_OF_ORDER = "AGENT_OF_ORDER"
        const val LOADER_FACTORY_ITEMS_OF_ORDER = "ITEMS_OF_ORDER"
        const val LOADER_FACTORY_TASKS_OF_ORDER = "TASKS_OF_ORDER"
        const val LOADER_FACTORY_TECHNOLOGIES_OF_ORDER = "TECHNOLOGIES_OF_ORDER"
        const val LOADER_FACTORY_PARAMETERS_OF_ORDER = "PARAMETERS_OF_ORDER"
        const val LOADER_FACTORY_CATEGORIES_OF_ORDER = "CATEGORIES_OF_ORDER"
        const val LOADER_FACTORY_WORKLOGS_OF_ORDER = "WORKLOGS_OF_ORDER"
        const val LOADER_FACTORY_TYPE_OF_ORDER = "TYPE_OF_ORDER"

        const val LOADER_FACTORY_STATISTICS_OF_ORDER = "STATISTICS_OF_ORDER"
        const val LOADER_FACTORY_DEPARTMENTS_STATISTICS_OF_ORDER = "DEPARTMENTS_STATISTICS_OF_ORDER"
        const val LOADER_FACTORY_ITEM_CATEGORIES_STATISTICS_OF_ORDER = "ITEM_CATEGORIES_STATISTICS_OF_ORDER"

        // ********** search references *************
        const val LOADER_FACTORY_GLOBAL_SEARCH_ITEMS = "GLOBAL_SEARCH_ITEMS"
        const val LOADER_FACTORY_GLOBAL_SEARCH_TASKS = "GLOBAL_SEARCH_TASKS"
        const val LOADER_FACTORY_GLOBAL_SEARCH_TECHNOLOGIES = "GLOBAL_SEARCH_TECHNOLOGIES"
        const val LOADER_FACTORY_GLOBAL_SEARCH_PARAMETERS = "GLOBAL_SEARCH_PARAMETERS"

        // customer references
        const val LOADER_FACTORY_ENTITIES_OF_CUSTOMER = "ENTITIES_OF_CUSTOMER"

        // task references
        const val LOADER_FACTORY_OPERATION_OF_TASK = "OPERATION_OF_TASK"
        const val LOADER_FACTORY_ITEM_OF_TASK = "ITEM_OF_TASK"
        const val LOADER_FACTORY_ORDER_OF_TASK = "ORDER_OF_TASK"
        const val LOADER_FACTORY_DEPARTMENTS_OF_TASK = "DEPARTMENTS_OF_TASK"
        const val LOADER_FACTORY_PARAMETERS_OF_TASK = "PARAMETERS_OF_TASK"

        // item references
        const val LOADER_FACTORY_STATISTICS_OF_ITEM = "STATISTICS_OF_ITEM"
        const val LOADER_FACTORY_WHO_ADDED_OF_ITEM = "WHO_ADDED_OF_ITEM"
        const val LOADER_FACTORY_TECHNOLOGIES_OF_ITEM = "TECHNOLOGIES_OF_ITEM"
        const val LOADER_FACTORY_PARAMETERS_OF_ITEM = "PARAMETERS_OF_ITEM"

    }

    fun create(instanceId: UUID): DataLoaderRegistry {
        val registry = DataLoaderRegistry()


        registry.register(LOADER_FACTORY_CUSTOMER_OF_ORDER, _order.customerLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_MANAGER_OF_ORDER, _order.managerLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_AGENT_OF_ORDER, _order.agentLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_ITEMS_OF_ORDER, _order.itemsLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_TASKS_OF_ORDER, _order.tasksLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_TECHNOLOGIES_OF_ORDER, _order.technologiesLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_PARAMETERS_OF_ORDER, _order.parametersLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_CATEGORIES_OF_ORDER, _order.categoriesLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_WORKLOGS_OF_ORDER, _order.worklogsLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_TYPE_OF_ORDER, _order.typeLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_STATISTICS_OF_ORDER, _order.statisticsLoader(userId = instanceId))
        registry.register(
            LOADER_FACTORY_DEPARTMENTS_STATISTICS_OF_ORDER,
            _order.departmentsStatisticsLoader(userId = instanceId)
        )
        registry.register(
            LOADER_FACTORY_ITEM_CATEGORIES_STATISTICS_OF_ORDER,
            _order.itemCategoryStatisticsLoader(userId = instanceId)
        )

        registry.register(LOADER_FACTORY_GLOBAL_SEARCH_ITEMS, _order.itemsSearchedLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_GLOBAL_SEARCH_TASKS, _order.tasksSearchedLoader(userId = instanceId))
        registry.register(
            LOADER_FACTORY_GLOBAL_SEARCH_TECHNOLOGIES,
            _order.technologiesSearchedLoader(userId = instanceId)
        )
        registry.register(LOADER_FACTORY_GLOBAL_SEARCH_PARAMETERS, _order.parametersSearchedLoader(userId = instanceId))

        registry.register(LOADER_FACTORY_ENTITIES_OF_CUSTOMER, _customer.entitiesLoader(userId = instanceId))
//        registry.register(CUSTOMER_OF_ORDER, forCustomer.dataLoaderCustomerOfOrder(userId = instanceId))
//        registry.register(AGENT_OF_ORDER, forAgent.dataLoaderAgentOfUser(userId = instanceId))
//        registry.register(MANAGER_OF_ORDER, forAgent.dataLoaderManagerOfOrder(userId = instanceId))
//        registry.register(CATEGORY_OF_ITEM_IN_ORDER, forCategory.dataLoaderCategoriesOfItemInOrder(userId = instanceId))

//        registry.register(ITEM_IN_ORDER, orderImpl.dataLoaderItemsInOrder(userId = instanceId))
//        registry.register(OPERATIONS_IN_ITEM, _item.dataLoaderOperationOfItem(userId = instanceId))
//        registry.register(CATEGORY_IN_ITEM, _item.dataLoaderCategoryInItem(userId = instanceId))
        registry.register(GROUP_IN_OPERATION_DATALOADER, forOperation.dataLoaderGroupInOperation(userId = instanceId))
//        registry.register(TASK_IN_ITEM_DATALOADER, _item.dataLoaderTaskOfItem(userId = instanceId))
//
//        registry.register(OPERATION_IN_TASK_DATALOADER, _task.dataLoaderOperationInTask(userId = instanceId))
//        registry.register(DEPARTMENTS_IN_TASK_DATALOADER, _task.dataLoaderDepartmentsInTask(userId = instanceId))
//        registry.register(COMMENTS_IN_TASK_DATALOADER, _task.dataLoaderCommentsInTask(userId = instanceId))
//        registry.register(ITEM_IN_TASK_DATALOADER, _task.dataLoaderItemInTask(userId = instanceId))
//        registry.register(ORDER_IN_TASK_DATALOADER, _task.dataLoaderOrderInTask(userId = instanceId))

        registry.register(LOADER_FACTORY_OPERATION_OF_TASK, _task.operationLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_ITEM_OF_TASK, _task.itemLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_ORDER_OF_TASK, _task.orderLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_DEPARTMENTS_OF_TASK, _task.departmentsLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_PARAMETERS_OF_TASK, _task.parametersLoader(userId = instanceId))




        registry.register(LOADER_FACTORY_STATISTICS_OF_ITEM, _item.statisticsLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_WHO_ADDED_OF_ITEM, _item.whoAddedLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_TECHNOLOGIES_OF_ITEM, _item.technologiesLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_PARAMETERS_OF_ITEM, _item.parametersLoader(userId = instanceId))
//        registry.register(ORDER_IN_TASK_DATALOADER, _task.dataLoaderOrderInTask(userId = instanceId))

        registry.register(CONTACT_INFO_OF_USER_DATALOADER, forUser.dataLoaderContactInfoOfUser(userId = instanceId))
        registry.register(PERSONAL_INFO_OF_USER_DATALOADER, forUser.dataLoaderPersonalInfoOfUser(userId = instanceId))


        registry.register(ACTOR_OF_COMMENT_DATALOADER, forComment.dataLoaderActorOfComment(userId = instanceId))
        registry.register(
            WORKCENTER_OF_DEPARTMENT_IN_TASK_DATALOADER,
            forDepartment.dataLoaderWorkcenterInDepartmentHavingTask(userId = instanceId)
        )
        registry.register(
            USER_IN_WORK_CENTER_WORKING_IN_TASK_DATALOADER,
            forWorkcenter.dataLoaderUserInWorkingCenterWorkingInTask(userId = instanceId)
        )

//        registry.register(USER_IN_TASK_DATALOADER, _task.dataLoaderUsersInTask(userId = instanceId))
        registry.register(ACTOR_IN_WORKLOG_DATALOADER, forWorklog.dataLoaderActorInWorklog(userId = instanceId))

        registry.register(
            OPERATION_IN_TECHNOLOGY_DATALOADER,
            _technology.dataLoaderOperationInTechnology(userId = instanceId)
        )

//        registry.register(
//            ACTOR_IN_DETAIL_OF_ORDER_DATALOADER,
//            _item.dataLoaderDetailActor(userId = instanceId)
//        )

//        registry.register(
//            TECHNOLOGIES_IN_DETAIL_OF_ORDER_DATALOADER,
//            _item.dataLoaderDetailTechnologies(userId = instanceId)
//        )

//        registry.register(TYPE_OF_ORDER_DATALOADER, orderImpl.dataLoaderTypeOfOrder(userId = instanceId))
//        registry.register(ITEM_IN_ITEM_DATALOADER, _item.dataLoaderItemAndItem(userId = instanceId))

        registry.register(
            POTENTIAL_VALUES_PARAMETER_DATALOADER,
            _parameter.dataLoaderPotentialValuesOfParameter(userId = instanceId)
        )
        return registry
    }


}

