package com.rsba.component_microservice.database

import com.rsba.component_microservice.domain.input.ItemCategoryInput
import com.rsba.component_microservice.domain.input.ItemInput
import com.rsba.component_microservice.domain.input.ItemAndOperationInput
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemDBQuery {

    fun onCreateOrEditCategoryOfItemQuery(input: ItemCategoryInput, token: UUID) =
        "SELECT on_create_or_edit_category_of_item('${Json.encodeToString(input)}', '$token')"

    fun onDeleteCategoryOfItemQuery(input: UUID, token: UUID) =
        "SELECT on_delete_category_of_item('$input','$token')"

    fun onCreateOrEditItem(input: ItemInput, token: UUID) =
        "SELECT on_create_or_edit_item('${Json.encodeToString(input)}', '$token')"

    fun onDeleteItem(input: UUID, token: UUID) =
        "SELECT on_delete_item('$input', '$token')"

    fun onRetrieveAllCategoryOfItemQuery(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_category_of_item('$first', ${after?.let { "'$it'" }},'$token')"

    fun onRetrieveAllItem(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_items('$first', ${after?.let { "'$it'" }},'$token')"

    fun onRetrieveAllItemHavingCategory(first: Int, after: UUID?, token: UUID) =
        "SELECT on_retrieve_items_having_category('$first', ${after?.let { "'$it'" }},'$token')"

    fun onAttachOperationWithItem(input: ItemAndOperationInput, token: UUID) =
        "SELECT on_attach_operation_with_item('${Json.encodeToString(input)}', '$token')"

    fun onDetachOperationWithItem(input: ItemAndOperationInput, token: UUID) =
        "SELECT on_detach_operation_with_item('${Json.encodeToString(input)}', '$token')"

    fun onRetrieveOperationsByItemId(itemId: UUID) =
        "SELECT on_retrieve_operations_by_item_id('$itemId')"

    fun onRetrieveCategoryByItemId(itemId: UUID) =
        "SELECT on_retrieve_category_by_item_id('$itemId')"
}
