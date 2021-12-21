package com.rsba.tasks_microservice.domain.queries

import com.rsba.tasks_microservice.data.dao.*
import com.rsba.tasks_microservice.data.service.usecase.queries.TaskQueries
import com.rsba.tasks_microservice.domain.exception.failOnNull
import com.rsba.tasks_microservice.domain.input.AbstractInput
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
sealed class QueryPicker<T : AbstractModel> {
    object Task : QueryPicker<TaskDao>()

    fun pick(): IBaseQuery<AbstractInput, AbstractModel> = when (this) {
        is Task -> TaskQueries
        else -> failOnNull()
    }
}