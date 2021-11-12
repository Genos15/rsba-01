package com.rsba.component_microservice.domain.queries

import com.rsba.component_microservice.data.dao.*
import com.rsba.component_microservice.data.service.usecase.queries.ItemCategoryQueries
import com.rsba.component_microservice.data.service.usecase.queries.ItemQueries
import com.rsba.component_microservice.data.service.usecase.queries.OperationQueries
import com.rsba.component_microservice.data.service.usecase.queries.TechnologyQueries
import com.rsba.component_microservice.domain.exception.failOnNull
import com.rsba.component_microservice.domain.input.AbstractInput
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
sealed class QueryPicker<T : AbstractModel> {
    object ItemCategory : QueryPicker<ItemCategoryDao>()
    object Item : QueryPicker<ItemDao>()
    object Operation : QueryPicker<OperationDao>()
    object Technology : QueryPicker<TechnologyDao>()

    fun pick(): IBaseQuery<AbstractInput, AbstractModel> = when (this) {
        is Technology -> TechnologyQueries
        is Operation -> OperationQueries
        is Item -> ItemQueries
        is ItemCategory -> ItemCategoryQueries
        else -> failOnNull()
    }
}