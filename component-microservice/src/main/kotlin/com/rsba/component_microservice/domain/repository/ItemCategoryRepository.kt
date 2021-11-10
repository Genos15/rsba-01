package com.rsba.component_microservice.domain.repository

import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.input.ItemCategoryInput
import com.rsba.component_microservice.domain.model.ElkGraph
import com.rsba.component_microservice.domain.model.ElkGraphItemCategoryNode
import com.rsba.component_microservice.domain.model.Item
import java.util.*

interface ItemCategoryRepository {

    suspend fun createOrEdit(input: ItemCategoryInput, token: UUID): Optional<ItemCategory>

    suspend fun delete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<ItemCategory>

    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<ItemCategory>

    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<ItemCategory>

    suspend fun children(
        id: UUID,
        first: Int,
        after: UUID?,
        token: UUID
    ): List<ItemCategory>

    suspend fun children(ids: Set<UUID>): Map<UUID, List<ItemCategory>>

    suspend fun count(token: UUID): Int

    suspend fun items(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Item>>

    suspend fun elk(
        token: UUID,
        from: UUID? = null,
        height: Int,
        width: Int,
    ): ElkGraph<ElkGraphItemCategoryNode>

}