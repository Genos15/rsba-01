package com.rsba.component_microservice.domain.format

import com.rsba.component_microservice.data.dao.AbstractModel
import java.util.*

interface IBaseQuery<in I, out R : AbstractModel> {

    fun createOrEdit(input: I, token: UUID): String

    fun delete(input: UUID, token: UUID): String

    fun retrieve(first: Int, after: UUID?, token: UUID): String

    fun search(input: String, first: Int, after: UUID?, token: UUID): String

    fun find(id: UUID, token: UUID): String

    fun count(token: UUID): String

}