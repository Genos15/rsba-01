package com.rsba.component_microservice.domain.repository

import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.input.ItemInput
import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.domain.model.MutationAction
import com.rsba.component_microservice.domain.model.Operation
import java.util.*

interface ItemRepository {

    suspend fun toCreateOrEdit(input: ItemInput, action: MutationAction? = null, token: UUID): Optional<Item>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<Item>

    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Item>

    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Item>

    suspend fun operations(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Operation>>

    suspend fun components(
        ids: Set<UUID>, first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Item>>

    suspend fun category(ids: Set<UUID>): Map<UUID, Optional<ItemCategory>>

    suspend fun count(token: UUID): Int

}