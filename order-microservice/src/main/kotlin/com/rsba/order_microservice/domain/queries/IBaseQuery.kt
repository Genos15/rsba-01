package com.rsba.order_microservice.domain.queries

import com.rsba.order_microservice.data.dao.AbstractModel
import com.rsba.order_microservice.domain.input.AbstractInput
import com.rsba.order_microservice.domain.model.AbstractLayer
import com.rsba.order_microservice.domain.model.AbstractStatus
import com.rsba.order_microservice.domain.model.Edition
import com.rsba.order_microservice.domain.model.MutationAction
import java.util.*

interface IBaseQuery<out I : AbstractInput, out R : AbstractModel> {

    fun createOrEdit(
        input: @UnsafeVariance I,
        token: UUID,
        action: MutationAction? = null,
        case: Edition? = null
    ): String

    fun delete(input: UUID, token: UUID): String

    fun retrieve(
        first: Int,
        after: UUID?,
        token: UUID,
        layer: AbstractLayer? = null,
        status: AbstractStatus? = null,
    ): String

    fun search(
        input: String,
        first: Int,
        after: UUID?,
        layer: AbstractLayer? = null,
        status: AbstractStatus? = null,
        token: UUID
    ): String

    fun find(id: UUID, token: UUID): String

    fun count(token: UUID, status: AbstractStatus? = null): String

}