package com.rsba.usermicroservice.repository

import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.AnalysisTemplate
import java.util.*
import kotlin.jvm.Throws

interface AnalysisTemplateRepository {
    @Throws(RuntimeException::class)
    suspend fun createOrEdit(input: CreateOrEditAnalysisTemplateInput, token: UUID): Optional<AnalysisTemplate>
    suspend fun delete(input: UUID, token: UUID): Boolean
    suspend fun retrieveAllAnalysisTemplates(first: Int, after: UUID?, token: UUID): MutableList<AnalysisTemplate>
}