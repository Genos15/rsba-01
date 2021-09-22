package com.rsba.usermicroservice.repository

import graphql.schema.DataFetchingEnvironment
import java.util.*
import kotlin.jvm.Throws

interface AbstractCRUDRepository<T, G, Y> {
    @Throws(RuntimeException::class)
    suspend fun onInsert(input: G): Optional<T>

    @Throws(RuntimeException::class)
    suspend fun onInsertAdminAndCompany(input: Y): Int {
        return 0
    }

    suspend fun onEdit(input: T, environment: DataFetchingEnvironment): Optional<T>
    suspend fun onDelete(input: T, environment: DataFetchingEnvironment): Int
    suspend fun onRetrieveOne(input: T, environment: DataFetchingEnvironment): Optional<T>
    suspend fun onRetrieveAll(first: Int = 0, after: UUID? = null, token: UUID): List<T>
}