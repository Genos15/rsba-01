package com.rsba.parameters_microservice.domain.queries

import com.rsba.parameters_microservice.data.dao.*
import com.rsba.parameters_microservice.domain.exception.failOnNull
import com.rsba.parameters_microservice.domain.input.AbstractInput

interface IQueryGuesser

inline fun <reified T : AbstractModel> query(): IBaseQuery<AbstractInput, AbstractModel> =
    when (T::class) {
        ParameterDao::class -> QueryPicker.Parameter.pick()
        else -> failOnNull()
    }