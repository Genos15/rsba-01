package com.rsba.component_microservice.domain.queries

import com.rsba.component_microservice.data.dao.AbstractModel
import com.rsba.component_microservice.domain.input.AbstractInput
import com.rsba.component_microservice.domain.model.Edition
import com.rsba.component_microservice.domain.model.EditionCase
import com.rsba.component_microservice.domain.model.MutationAction
import java.util.*

interface IBaseQuery<out I : AbstractInput, out R : AbstractModel> {

    fun createOrEdit(
        input: @UnsafeVariance I,
        token: UUID,
        action: MutationAction? = null,
        case: Edition<EditionCase>? = null
    ): String

    fun delete(input: UUID, token: UUID): String

    fun retrieve(first: Int, after: UUID?, token: UUID): String

    fun search(input: String, first: Int, after: UUID?, token: UUID): String

    fun find(id: UUID, token: UUID): String

    fun count(token: UUID): String

}