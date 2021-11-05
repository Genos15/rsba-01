package com.rsba.component_microservice.domain.repository

import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.input.ItemCategoryInput
import java.util.*

interface ItemCategoryRepository {

    suspend fun createOrEdit(input: ItemCategoryInput, token: UUID): Optional<ItemCategory>

    suspend fun delete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<ItemCategory>

    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<ItemCategory>

    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<ItemCategory>

}