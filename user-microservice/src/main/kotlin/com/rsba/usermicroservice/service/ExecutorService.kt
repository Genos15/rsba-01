package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.domain.input.ExecutorInput
import com.rsba.usermicroservice.domain.model.Executor
import com.rsba.usermicroservice.repository.ExecutorRepository
import com.rsba.usermicroservice.service.implementation.executors.CreateOrEditExecutorImpl
import com.rsba.usermicroservice.service.implementation.executors.DeleteExecutorImpl
import com.rsba.usermicroservice.service.implementation.executors.RetrieveExecutorImpl
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExecutorService(private val database: DatabaseClient) : ExecutorRepository, CreateOrEditExecutorImpl,
    DeleteExecutorImpl, RetrieveExecutorImpl {

    /**
     * @param token the key allowing the request to proceed.
     * @param input main Args, feedback object to save.
     * @return {@link List<Executor>}
     */
    override suspend fun createOrEdit(input: ExecutorInput, token: UUID): Optional<Executor> =
        createOrEditImpl(input = input, token = token, database = database)

    /**
     * @param token the key allowing the request to proceed.
     * @param input main Args, feedback object to save.
     * @return {@link List<Executor>}
     */
    override suspend fun renewPin(input: ExecutorInput, token: UUID): Optional<Executor> =
        renewPinImpl(input = input, token = token, database = database)

    /**
     * @param token the key allowing the request to proceed.
     * @param input, unique reference of the feedback to delete.
     * @return {@link <Boolean> depending on the result of the request}
     */
    override suspend fun delete(input: UUID, token: UUID): Boolean =
        deleteImpl(database = database, input = input, token = token)

    /**
     * @param token the key allowing the request to proceed.
     * @param first number of feedback to retrieve.
     * @param after last feedback retrieve in the previous request.
     * @return {@link List<Executor>}
     */
    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Executor> =
        retrieveImpl(database = database, first = first, after = after, token = token)

    /**
     * @param token the key allowing the request to proceed.
     * @param first number of feedback to retrieve.
     * @param after last feedback retrieve in the previous request.
     * @param input the sequence of character to search in the datasource.
     * @return {@link List<Executor>}
     */
    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Executor> =
        retrieveImpl(database = database, first = first, after = after, input = input, token = token)

    /**
     * @param token the key allowing the request to proceed.
     * @param id, unique reference of the feedback to get.
     * @return {@link List<Executor>}
     */
    override suspend fun retrieveById(id: UUID, token: UUID): Optional<Executor> =
        retrieveByIdImpl(id = id, token = token, database = database)
}