package  com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.domain.format.CustomPageInfo
import kotlinx.serialization.Serializable
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import graphql.relay.Connection
import graphql.relay.DefaultConnection

@Serializable
@ModelType(_class = ModelTypeCase.orders_search_instances)
data class OrderSearchInstance(
    val input: OrderSearchInputValue,
    val items: Connection<Item> = DefaultConnection(
        emptyList(),
        CustomPageInfo<Item>(edges = emptyList(), first = 0, after = null)
    ),
    val tasks: Connection<Task> = DefaultConnection(
        emptyList(),
        CustomPageInfo<Task>(edges = emptyList(), first = 0, after = null)
    ),
    val technologies: Connection<Technology> = DefaultConnection(
        emptyList(),
        CustomPageInfo<Technology>(edges = emptyList(), first = 0, after = null)
    ),
    val parameters: Connection<Parameter> = DefaultConnection(
        emptyList(),
        CustomPageInfo<Parameter>(edges = emptyList(), first = 0, after = null)
    ),
)
