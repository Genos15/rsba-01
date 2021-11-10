package com.rsba.order_microservice.data.service.implementation.items

import com.rsba.order_microservice.database.ItemDBHandler
import com.rsba.order_microservice.database.ItemDBQueries
import com.rsba.order_microservice.domain.input.ItemAndItemInput
import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.exception.CustomGraphQLError
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface EditComponentAndItemImpl {

    suspend fun addItemAndItemImplFn(input: ItemAndItemInput, token: UUID, database: DatabaseClient): Optional<Item> =
        database.sql(ItemDBQueries.addOrEditComponentInItem(input = input, token = token))
            .map { row -> ItemDBHandler.one(row = row) }
            .first()
            .onErrorResume {
                println { "addItemAndItemImplFn = ${it.message}" }
                if (it is CustomGraphQLError)
                    throw CustomGraphQLError(message = "НЕВОЗМОЖНО ПОЛУЧИТЬ ССЫЛКУ НА СЛЕДУЮЩИЙ ЗАКАЗ. ПОЖАЛУЙСТА, СВЯЖИТЕСЬ СО СЛУЖБОЙ ПОДДЕРЖКИ")
                else throw it
            }
            .log()
            .awaitFirst()

    suspend fun removeItemAndItemImplFn(
        input: ItemAndItemInput,
        token: UUID,
        database: DatabaseClient
    ): Optional<Item> =
        database.sql(ItemDBQueries.removeComponentInItem(input = input, token = token))
            .map { row -> ItemDBHandler.one(row = row) }
            .first()
            .onErrorResume {
                println { "addItemAndItemImplFn = ${it.message}" }
                throw CustomGraphQLError(message = "НЕВОЗМОЖНО ПОЛУЧИТЬ ССЫЛКУ НА СЛЕДУЮЩИЙ ЗАКАЗ. ПОЖАЛУЙСТА, СВЯЖИТЕСЬ СО СЛУЖБОЙ ПОДДЕРЖКИ")
            }
            .log()
            .awaitFirst()
}