package com.rsba.tasks_microservice.domain.queries

import com.rsba.tasks_microservice.data.dao.AbstractModel
import com.rsba.tasks_microservice.domain.input.AbstractInput
import com.rsba.tasks_microservice.domain.model.*
import java.time.OffsetDateTime
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
        after: UUID? = null,
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
        token: UUID
    ): String

    fun search(
        input: String,
        first: Int,
        after: UUID? = null,
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
        token: UUID
    ): String

    fun find(id: UUID, token: UUID): String

    fun count(
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        token: UUID
    ): String

}