package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.domain.input.CreateOrEditAnalysisTemplateInput
import com.rsba.usermicroservice.domain.model.AnalysisTemplate
import com.rsba.usermicroservice.query.database.AT_DBHandler
import com.rsba.usermicroservice.query.database.AT_DBQueries
import com.rsba.usermicroservice.repository.AnalysisTemplateRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class AnalysisTemplateService(private val database: DatabaseClient) : AnalysisTemplateRepository {

    override suspend fun createOrEdit(input: CreateOrEditAnalysisTemplateInput, token: UUID): Optional<AnalysisTemplate> =
        database.sql(AT_DBQueries.createOrEdit(input = input, token = token))
            .map { row, meta -> AT_DBHandler.one(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun delete(input: UUID, token: UUID): Boolean =
        database.sql(AT_DBQueries.delete(id = input, token = token))
            .map { row, meta -> AT_DBHandler.count(row = row, meta = meta) }
            .first()
            .map { it > 0 }
            .awaitFirstOrElse { false }

    override suspend fun retrieveAllAnalysisTemplates(
        first: Int,
        after: UUID?,
        token: UUID
    ): MutableList<AnalysisTemplate> =
        database.sql(AT_DBQueries.retrieveAll(first = first, after = after, token = token))
            .map { row, meta -> AT_DBHandler.all(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { mutableListOf() }


}