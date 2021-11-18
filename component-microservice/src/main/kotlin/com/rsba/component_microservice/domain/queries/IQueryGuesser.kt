package com.rsba.component_microservice.domain.queries

import com.rsba.component_microservice.data.dao.*
import com.rsba.component_microservice.domain.exception.failOnNull
import com.rsba.component_microservice.domain.input.AbstractInput

interface IQueryGuesser

inline fun <reified T : AbstractModel> query(): IBaseQuery<AbstractInput, AbstractModel> =
    when (T::class) {
        TechnologyDao::class -> QueryPicker.Technology.pick()
        OperationDao::class -> QueryPicker.Operation.pick()
        ItemDao::class -> QueryPicker.Item.pick()
        ItemCategoryDao::class -> QueryPicker.ItemCategory.pick()
        else -> failOnNull()
    }