package com.rsba.order_microservice.service.implementation.items

import com.rsba.order_microservice.database.ItemDBHandler
import com.rsba.order_microservice.database.ItemDBQueries
import com.rsba.order_microservice.domain.input.ItemAndItemInput
import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.exception.CustomGraphQLError
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface ItemAndItemImpl {

    suspend fun addItemAndItemImplFn(input: ItemAndItemInput, token: UUID, database: DatabaseClient): Optional<Item> =
        database.sql(ItemDBQueries.addItemInItem(input = input, token = token))
            .map { row -> ItemDBHandler.one(row = row) }
            .first()
            .onErrorResume {
                println { "addItemAndItemImplFn = ${it.message}" }
                throw CustomGraphQLError(message = "НЕВОЗМОЖНО ПОЛУЧИТЬ ССЫЛКУ НА СЛЕДУЮЩИЙ ЗАКАЗ. ПОЖАЛУЙСТА, СВЯЖИТЕСЬ СО СЛУЖБОЙ ПОДДЕРЖКИ")
            }
            .log()
            .awaitFirst()

    suspend fun removeItemAndItemImplFn(
        input: ItemAndItemInput,
        token: UUID,
        database: DatabaseClient
    ): Optional<Item> =
        database.sql(ItemDBQueries.removeItemInItem(input = input, token = token))
            .map { row -> ItemDBHandler.one(row = row) }
            .first()
            .onErrorResume {
                println { "addItemAndItemImplFn = ${it.message}" }
                throw CustomGraphQLError(message = "НЕВОЗМОЖНО ПОЛУЧИТЬ ССЫЛКУ НА СЛЕДУЮЩИЙ ЗАКАЗ. ПОЖАЛУЙСТА, СВЯЖИТЕСЬ СО СЛУЖБОЙ ПОДДЕРЖКИ")
            }
            .log()
            .awaitFirst()
}