package com.rsba.tasks_microservice.domain.queries

import com.rsba.tasks_microservice.data.dao.*
import com.rsba.tasks_microservice.data.service.usecase.queries.CommentQueries
import com.rsba.tasks_microservice.data.service.usecase.queries.TaskQueries
import com.rsba.tasks_microservice.data.service.usecase.queries.TaskSetQueries
import com.rsba.tasks_microservice.domain.exception.failOnNull
import com.rsba.tasks_microservice.domain.input.AbstractInput
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
sealed class QueryPicker<T : AbstractModel> {
    object Task : QueryPicker<TaskDao>()
    object TaskSet : QueryPicker<TaskSetDao>()
    object Comment : QueryPicker<CommentDao>()

    fun pick(): IBaseQuery<AbstractInput, AbstractModel> = when (this) {
        is Task -> TaskQueries
        is Comment -> CommentQueries
        is TaskSet -> TaskSetQueries
        else -> failOnNull()
    }
}