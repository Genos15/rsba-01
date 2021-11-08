package com.rsba.component_microservice.domain.repository

import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.input.ItemInput
import com.rsba.component_microservice.domain.input.ItemTechnologyInput
import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.domain.model.Operation
import graphql.schema.DataFetchingEnvironment
import java.util.*

interface ItemRepository {

    suspend fun toCreateOrEdit(input: ItemInput, token: UUID): Optional<Item>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<Item>

    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Item>

    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Item>

    suspend fun toAttachOperation(input: ItemInput, token: UUID): Optional<Item>

    suspend fun toAttachSubItem(input: ItemInput, token: UUID): Optional<Item>

    suspend fun toDetachOperation(input: ItemInput, token: UUID): Optional<Item>

    suspend fun toDetachSubItem(input: ItemInput, token: UUID): Optional<Item>

    suspend fun operations(ids: Set<UUID>): Map<UUID, List<Operation>>

    suspend fun components(ids: Set<UUID>): Map<UUID, List<Item>>

    suspend fun category(ids: Set<UUID>): Map<UUID, Optional<ItemCategory>>

    suspend fun toAttachTechnology(input: ItemTechnologyInput, token: UUID): Optional<Item>

    suspend fun itemsByCategoryId(categoryId: UUID, first: Int, after: UUID?, token: UUID): List<Item>

    suspend fun toImportItemFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean>

}