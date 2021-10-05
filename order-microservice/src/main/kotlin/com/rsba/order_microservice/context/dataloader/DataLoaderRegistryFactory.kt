package  com.rsba.order_microservice.context.dataloader

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
    private val orderImpl: OrderDataLoaderImpl,
    private val _item: ItemDataLoaderImpl,
    private val forOperation: OperationDataLoaderImpl,
    private val forTask: TaskDataLoaderImpl,
    private val forUser: UserDataLoaderImpl,
    private val forComment: CommentDataLoaderImpl,
    private val forDepartment: DepartmentDataLoaderImpl,
    private val forWorkcenter: WorkCenterDataLoaderImpl,
    private val forWorklog: WorklogDataLoaderImpl,
    private val _technology: TechnologyDataLoaderImpl
) {

    companion object {
        const val CUSTOMERS_IN_CUSTOMER = "CUSTOMERS_IN_CUSTOMER"
        const val CUSTOMER_OF_ORDER = "CUSTOMER_OF_ORDER"

        const val AGENT_OF_ORDER = "AGENT_OF_ORDER"
        const val MANAGER_OF_ORDER = "MANAGER_OF_ORDER"

        const val CATEGORY_OF_ITEM_IN_ORDER = "CATEGORY_OF_ITEM_IN_ORDER"

        //        const val CATEGORY_OF_ITEM_IN_ORDER_POST_MVP = "CATEGORY_OF_ITEM_IN_ORDER_POST_MVP"
        const val ITEM_IN_ORDER = "ITEM_IN_ORDER"
        const val OPERATIONS_IN_ITEM = "OPERATIONS_IN_ITEM"
        const val CATEGORY_IN_ITEM = "CATEGORY_IN_ITEM"
        const val GROUP_IN_OPERATION_DATALOADER = "GROUP_IN_OPERATION_DATALOADER"
        const val TASK_IN_ITEM_DATALOADER = "TASK_IN_ITEM_DATALOADER"


        const val OPERATION_IN_TASK_DATALOADER = "OPERATION_IN_TASK_DATALOADER"
        const val DEPARTMENTS_IN_TASK_DATALOADER = "DEPARTMENTS_IN_TASK_DATALOADER"
        const val COMMENTS_IN_TASK_DATALOADER = "COMMENTS_IN_TASK_DATALOADER"
        const val ITEM_IN_TASK_DATALOADER = "ITEM_IN_TASK_DATALOADER"

        const val ORDER_IN_TASK_DATALOADER = "ORDER_IN_TASK_DATALOADER"

        const val PERSONAL_INFO_OF_USER_DATALOADER = "PERSONAL_INFO_OF_USER_DATALOADER"
        const val CONTACT_INFO_OF_USER_DATALOADER = "CONTACT_INFO_OF_USER_DATALOADER"

        const val ACTOR_OF_COMMENT_DATALOADER = "ACTOR_OF_COMMENT_DATALOADER"

        const val WORKCENTER_OF_DEPARTMENT_IN_TASK_DATALOADER = "WORKCENTER_OF_DEPARTMENT_IN_TASK_DATALOADER"
        const val USER_IN_WORK_CENTER_WORKING_IN_TASK_DATALOADER = "USER_IN_WORK_CENTER_WORKING_IN_TASK_DATALOADER"
        const val USER_IN_TASK_DATALOADER = "USER_IN_TASK_DATALOADER"

        const val ACTOR_IN_WORKLOG_DATALOADER = "ACTOR_IN_WORKLOG_DATALOADER"
        const val OPERATION_IN_TECHNOLOGY_DATALOADER = "OPERATION_IN_TECHNOLOGY_DATALOADER"

        const val ACTOR_IN_DETAIL_OF_ORDER_DATALOADER = "ACTOR_IN_DETAIL_OF_ORDER_DATALOADER"

        const val TECHNOLOGIES_IN_DETAIL_OF_ORDER_DATALOADER = "TECHNOLOGIES_IN_DETAIL_OF_ORDER_DATALOADER"

        const val TYPE_OF_ORDER_DATALOADER = "TYPE_OF_ORDER_DATALOADER"

        const val ITEM_IN_ITEM_DATALOADER = "TYPE_OF_ORDER_DATALOADER"

    }

    fun create(instanceId: UUID): DataLoaderRegistry {
        logger.warn { "+DataLoaderRegistryFactory -> create" }
        val registry = DataLoaderRegistry()
        registry.register(CUSTOMERS_IN_CUSTOMER, forCustomer.dataLoaderEntitiesOfCustomer(userId = instanceId))
        registry.register(CUSTOMER_OF_ORDER, forCustomer.dataLoaderCustomerOfOrder(userId = instanceId))
        registry.register(AGENT_OF_ORDER, forAgent.dataLoaderAgentOfUser(userId = instanceId))
        registry.register(MANAGER_OF_ORDER, forAgent.dataLoaderManagerOfOrder(userId = instanceId))
        registry.register(CATEGORY_OF_ITEM_IN_ORDER, forCategory.dataLoaderCategoriesOfItemInOrder(userId = instanceId))

        registry.register(ITEM_IN_ORDER, orderImpl.dataLoaderItemsInOrder(userId = instanceId))
        registry.register(OPERATIONS_IN_ITEM, _item.dataLoaderOperationOfItem(userId = instanceId))
        registry.register(CATEGORY_IN_ITEM, _item.dataLoaderCategoryInItem(userId = instanceId))
        registry.register(GROUP_IN_OPERATION_DATALOADER, forOperation.dataLoaderGroupInOperation(userId = instanceId))
        registry.register(TASK_IN_ITEM_DATALOADER, _item.dataLoaderTaskOfItem(userId = instanceId))

        registry.register(OPERATION_IN_TASK_DATALOADER, forTask.dataLoaderOperationInTask(userId = instanceId))
        registry.register(DEPARTMENTS_IN_TASK_DATALOADER, forTask.dataLoaderDepartmentsInTask(userId = instanceId))
        registry.register(COMMENTS_IN_TASK_DATALOADER, forTask.dataLoaderCommentsInTask(userId = instanceId))
        registry.register(ITEM_IN_TASK_DATALOADER, forTask.dataLoaderItemInTask(userId = instanceId))
        registry.register(ORDER_IN_TASK_DATALOADER, forTask.dataLoaderOrderInTask(userId = instanceId))

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

        registry.register(USER_IN_TASK_DATALOADER, forTask.dataLoaderUsersInTask(userId = instanceId))
        registry.register(ACTOR_IN_WORKLOG_DATALOADER, forWorklog.dataLoaderActorInWorklog(userId = instanceId))

        registry.register(
            OPERATION_IN_TECHNOLOGY_DATALOADER,
            _technology.dataLoaderOperationInTechnology(userId = instanceId)
        )

        registry.register(
            ACTOR_IN_DETAIL_OF_ORDER_DATALOADER,
            _item.dataLoaderDetailActor(userId = instanceId)
        )

        registry.register(
            TECHNOLOGIES_IN_DETAIL_OF_ORDER_DATALOADER,
            _item.dataLoaderDetailTechnologies(userId = instanceId)
        )

        registry.register(TYPE_OF_ORDER_DATALOADER, orderImpl.dataLoaderTypeOfOrder(userId = instanceId))
        registry.register(ITEM_IN_ITEM_DATALOADER, _item.dataLoaderItemAndItem(userId = instanceId))
        return registry
    }


}

