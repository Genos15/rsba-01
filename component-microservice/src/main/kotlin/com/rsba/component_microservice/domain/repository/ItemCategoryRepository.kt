package com.rsba.component_microservice.domain.repository

import com.rsba.component_microservice.domain.input.ItemCategoryInput
import com.rsba.component_microservice.domain.model.*
import java.time.OffsetDateTime
import java.util.*

interface ItemCategoryRepository {

    suspend fun createOrEdit(
        input: ItemCategoryInput,
        action: MutationAction? = null,
        token: UUID
    ): Optional<ItemCategory>

    suspend fun delete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<ItemCategory>

    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<ItemCategory>

    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<ItemCategory>

    suspend fun subCategories(
        ids: Set<UUID>, first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<ItemCategory>>

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

    suspend fun usages(
        first: Int,
        after: UUID?,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        token: UUID
    ): List<InformationUsage>

    suspend fun usages(
        input: String,
        first: Int, after: UUID?,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        token: UUID
    ): List<InformationUsage>

    suspend fun usage(
        input: UUID,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        token: UUID
    ): Optional<InformationUsage>


}