package com.rsba.component_microservice.data.service.implementations

import com.rsba.component_microservice.domain.input.TechnologyInput
import com.rsba.component_microservice.domain.model.MutationAction
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.model.Technology
import com.rsba.component_microservice.domain.repository.TechnologyRepository
import com.rsba.component_microservice.domain.usecase.common.*
import com.rsba.component_microservice.domain.usecase.custom.technology.RetrieveOperationTechnologiesUseCase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class TechnologyService(
    private val database: DatabaseClient,
    @Qualifier("create_edit_technology") private val createOrEditUseCase: CreateOrEditUseCase<TechnologyInput, Technology>,
    @Qualifier("delete_technology") private val deleteUseCase: DeleteUseCase<Technology>,
    @Qualifier("find_technology") private val findUseCase: FindUseCase<Technology>,
    @Qualifier("retrieve_technology") private val retrieveUseCase: RetrieveUseCase<Technology>,
    @Qualifier("search_technology") private val searchUseCase: SearchUseCase<Technology>,
    @Qualifier("count_technology") private val countUseCase: CountUseCase,
    private val operationsUseCase: RetrieveOperationTechnologiesUseCase
) : TechnologyRepository {

    override suspend fun toCreateOrEdit(
        input: TechnologyInput,
        action: MutationAction?,
        token: UUID
    ): Optional<Technology> =
        createOrEditUseCase(database = database, input = input, action = action, token = token)

    override suspend fun toDelete(input: UUID, token: UUID): Boolean =
        deleteUseCase(database = database, input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<Technology> =
        findUseCase(database = database, id = id, token = token)

    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Technology> =
        retrieveUseCase(database = database, first = first, after = after, token = token)

    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Technology> =
        searchUseCase(database = database, first = first, after = after, token = token, input = input)

    override suspend fun count(token: UUID): Int =
        countUseCase(database = database, token = token)

    override suspend fun operations(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Operation>> =
        operationsUseCase(database = database, ids = ids, token = token, first = first, after = after)

}