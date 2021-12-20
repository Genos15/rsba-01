package com.rsba.parameters_microservice.data.service.implementations

import com.rsba.parameters_microservice.domain.input.ParameterInput
import com.rsba.parameters_microservice.domain.model.MutationAction
import com.rsba.parameters_microservice.domain.model.Parameter
import com.rsba.parameters_microservice.domain.repository.ParameterRepository
import com.rsba.parameters_microservice.domain.usecase.common.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class ParameterService(
    private val database: DatabaseClient,
    @Qualifier("create_edit_parameter") private val createOrEditUseCase: CreateOrEditUseCase<ParameterInput, Parameter>,
    @Qualifier("delete_parameter") private val deleteUseCase: DeleteUseCase<Parameter>,
    @Qualifier("find_parameter") private val findUseCase: FindUseCase<Parameter>,
    @Qualifier("retrieve_parameters") private val retrieveUseCase: RetrieveUseCase<Parameter>,
    @Qualifier("search_parameters") private val searchUseCase: SearchUseCase<Parameter>,
    @Qualifier("count_parameters") private val countUseCase: CountUseCase
) : ParameterRepository {

    override suspend fun toCreateOrEdit(
        input: ParameterInput,
        action: MutationAction?,
        token: UUID
    ): Optional<Parameter> =
        createOrEditUseCase(database = database, input = input, action = action, token = token)

    override suspend fun toDelete(input: UUID, token: UUID): Boolean =
        deleteUseCase(database = database, input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<Parameter> =
        findUseCase(database = database, id = id, token = token)

    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Parameter> =
        retrieveUseCase(database = database, first = first, after = after, token = token)

    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Parameter> =
        searchUseCase(database = database, first = first, after = after, token = token, input = input)

    override suspend fun count(token: UUID): Int =
        countUseCase(database = database, token = token)

}