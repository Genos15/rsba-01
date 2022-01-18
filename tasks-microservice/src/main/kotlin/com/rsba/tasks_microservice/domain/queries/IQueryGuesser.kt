package com.rsba.tasks_microservice.domain.queries

import com.rsba.tasks_microservice.data.dao.*
import com.rsba.tasks_microservice.domain.exception.failOnNull
import com.rsba.tasks_microservice.domain.input.AbstractInput

interface IQueryGuesser

inline fun <reified T : AbstractModel> query(): IBaseQuery<AbstractInput, AbstractModel> =
    when (T::class) {
        TaskDao::class -> QueryPicker.Task.pick()
        CommentDao::class -> QueryPicker.Comment.pick()
        TaskSetDao::class -> QueryPicker.TaskSet.pick()
        else -> failOnNull()
    }