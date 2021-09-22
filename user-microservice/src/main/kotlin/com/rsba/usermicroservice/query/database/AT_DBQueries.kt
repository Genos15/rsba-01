package com.rsba.usermicroservice.query.database

import com.rsba.usermicroservice.domain.input.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object AT_DBQueries {

    fun createOrEdit(input: CreateOrEditAnalysisTemplateInput, token: UUID): String =
        "SELECT on_create_or_edit_analysis_template('${Json.encodeToString(input)}', '$token')"

    fun delete(id: UUID, token: UUID): String =
        "SELECT on_delete_analysis_template('$id', '$token')"

    fun retrieveAll(first: Int, after: UUID?, token: UUID): String =
        "SELECT on_retrieve_analysis_templates('$first', ${after?.let { "'$it'" }},'$token')"
}
