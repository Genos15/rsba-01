package com.rsba.file_microservice.database

import org.springframework.stereotype.Component


@Component
class PhotoDatabaseQuery {
    fun onRetrieveAllCategoryOfItemQuery() =
        "SELECT on_retrieve_category_of_item('')"

    fun onRetrieveAllItem() =
        "SELECT on_retrieve_items('')"
}
