package com.rsba.order_microservice.domain.usecase.custom.order

import com.rsba.order_microservice.domain.input.ItemInput
import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.model.MutationAction
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface EditItemUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        input: ItemInput,
        token: UUID,
        action: MutationAction? = null
    ): Optional<Item>
}