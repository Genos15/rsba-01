package com.rsba.usermicroservice.query.database

import com.rsba.usermicroservice.domain.input.ModuleWithPermission
import mu.KotlinLogging
import java.util.*

object PermissionDatabaseQueries {

    private val logger = KotlinLogging.logger {}

    fun retrieveByModuleId(moduleId: UUID): String {
        val req = "SELECT on_retrieve_permission_by_module_id('$moduleId','')"
        logger.warn { req }
        return req
    }

    fun retrieveByRoleId(roleId: UUID): String {
        val req = "SELECT on_retrieve_permission_by_role_id('$roleId','')"
        logger.warn { req }
        return req
    }

    fun onRetrievePermissionState(instance: ModuleWithPermission): String =
        "SELECT on_retrieve_permission_state('${instance.id}','${instance.roleId}', '')"

}
