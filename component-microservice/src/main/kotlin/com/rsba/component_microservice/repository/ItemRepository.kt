package com.rsba.component_microservice.repository

import com.rsba.component_microservice.domain.model.CategoryOfItem
import com.rsba.component_microservice.domain.input.CreateOrEditCategoryOfItemInput
import com.rsba.component_microservice.domain.input.CreateOrEditItemInput
import com.rsba.component_microservice.domain.input.ItemAndOperationInput
import com.rsba.component_microservice.domain.input.ItemTechnologyInput
import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.domain.model.Operation
import graphql.schema.DataFetchingEnvironment
import java.util.*

interface ItemRepository {

    suspend fun createOrEditItem(input: CreateOrEditItemInput, token: UUID): Optional<Item>

    suspend fun createOrEditCategoryOfItem(
        input: CreateOrEditCategoryOfItemInput,
        token: UUID
    ): Optional<CategoryOfItem>

    suspend fun deleteCategoryOfItem(input: UUID, token: UUID): Int

    suspend fun deleteItem(input: UUID, token: UUID): Int

    suspend fun onRetrieveItem(first: Int, after: UUID?, token: UUID): List<Item>

    suspend fun onRetrieveItemHavingCategory(first: Int, after: UUID?, token: UUID): List<Item>

    suspend fun onRetrieveCategoryOfItem(first: Int, after: UUID?, token: UUID): List<CategoryOfItem>

    suspend fun attachOperationToItem(input: ItemAndOperationInput, token: UUID): Optional<Item>

    suspend fun detachOperationToItem(input: ItemAndOperationInput, token: UUID): Optional<Item>

    suspend fun retrieveOperationInItem(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, MutableList<Operation>>

    suspend fun retrieveCategoryInItem(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, CategoryOfItem?>

    suspend fun attachTechnology(input: ItemTechnologyInput, token: UUID): Optional<Item>

    suspend fun itemsByCategoryId(categoryId: UUID, first: Int, after: UUID?, token: UUID): List<Item>

    suspend fun importItemFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean>

}