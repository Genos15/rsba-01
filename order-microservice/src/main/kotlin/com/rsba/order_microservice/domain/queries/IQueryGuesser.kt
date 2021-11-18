package com.rsba.order_microservice.domain.queries

import com.rsba.order_microservice.data.dao.AbstractModel
import com.rsba.order_microservice.data.dao.OrderDao
import com.rsba.order_microservice.domain.exception.failOnNull
import com.rsba.order_microservice.domain.input.AbstractInput

interface IQueryGuesser

inline fun <reified T : AbstractModel> IQueryGuesser.query(): IBaseQuery<AbstractInput, AbstractModel> =
    when (T::class) {
        OrderDao::class -> QueryPicker.Order.pick()
        else -> failOnNull()
    }