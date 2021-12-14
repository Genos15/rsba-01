package com.rsba.parameters_microservice.domain.queries

import com.rsba.parameters_microservice.data.dao.*
import com.rsba.parameters_microservice.data.service.usecase.queries.ParameterQueries
import com.rsba.parameters_microservice.domain.exception.failOnNull
import com.rsba.parameters_microservice.domain.input.AbstractInput
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
sealed class QueryPicker<T : AbstractModel> {
    object Parameter : QueryPicker<ParameterDao>()

    fun pick(): IBaseQuery<AbstractInput, AbstractModel> = when (this) {
        is Parameter -> ParameterQueries
        else -> failOnNull()
    }
}