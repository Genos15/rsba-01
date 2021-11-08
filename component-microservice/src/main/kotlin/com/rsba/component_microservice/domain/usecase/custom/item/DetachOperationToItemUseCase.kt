package com.rsba.component_microservice.domain.usecase.custom.item

import com.rsba.component_microservice.domain.input.ItemInput
import com.rsba.component_microservice.domain.model.Item
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface DetachOperationToItemUseCase {
    suspend operator fun invoke(database: DatabaseClient, input: ItemInput, token: UUID): Optional<Item>
}