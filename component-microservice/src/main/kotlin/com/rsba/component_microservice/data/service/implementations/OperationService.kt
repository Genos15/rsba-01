package com.rsba.component_microservice.data.service.implementations

import com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.*
import com.rsba.component_microservice.domain.repository.OperationRepository
import com.rsba.component_microservice.domain.usecase.common.*
import com.rsba.component_microservice.domain.usecase.custom.operation.RetrieveOperationDepartmentsUseCase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class OperationService(
    private val database: DatabaseClient,
    @Qualifier("create_edit_operation") private val createOrEditUseCase: CreateOrEditUseCase<OperationInput, Operation>,
    @Qualifier("delete_operation") private val deleteUseCase: DeleteUseCase<Operation>,
    @Qualifier("find_operation") private val findUseCase: FindUseCase<Operation>,
    @Qualifier("retrieve_operation") private val retrieveUseCase: RetrieveUseCase<Operation>,
    @Qualifier("search_operation") private val searchUseCase: SearchUseCase<Operation>,
    @Qualifier("count_operation") private val countUseCase: CountUseCase,
    private val departmentsUseCase: RetrieveOperationDepartmentsUseCase
) : OperationRepository {

    override suspend fun toCreateOrEdit(
        input: OperationInput,
        action: MutationAction?,
        case: OperationEdition?,
        token: UUID
    ): Optional<Operation> =
        createOrEditUseCase(database = database, input = input, token = token, action = action, case = case)

    override suspend fun toDelete(input: UUID, token: UUID): Boolean =
        deleteUseCase(database = database, input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<Operation> =
        findUseCase(database = database, id = id, token = token)

    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Operation> =
        retrieveUseCase(database = database, first = first, after = after, token = token)

    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Operation> =
        searchUseCase(database = database, first = first, after = after, token = token, input = input)

    override suspend fun count(token: UUID): Int =
        countUseCase(database = database, token = token)

    override suspend fun departments(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Group>> =
        departmentsUseCase(database = database, ids = ids, token = token, first = first, after = after)

}