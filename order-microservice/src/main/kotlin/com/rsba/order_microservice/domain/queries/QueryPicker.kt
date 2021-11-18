package com.rsba.order_microservice.domain.queries

import com.rsba.order_microservice.data.dao.AbstractModel
import com.rsba.order_microservice.data.dao.OrderDao
import com.rsba.order_microservice.data.service.usecase.queries.OrderQueries
import com.rsba.order_microservice.domain.exception.failOnNull
import com.rsba.order_microservice.domain.input.AbstractInput
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
sealed class QueryPicker<T : AbstractModel> {
    object Order : QueryPicker<OrderDao>()

    fun pick(): IBaseQuery<AbstractInput, AbstractModel> = when (this) {
        is Order -> OrderQueries
        else -> failOnNull()
    }
}