package com.rsba.usermicroservice.query.database

import mu.KotlinLogging
import java.util.*

object ContactInfoDatabaseQueries {

    private val logger = KotlinLogging.logger {}

    fun onRetrieveByUserId(userId: UUID): String = "SELECT on_retrieve_contacts_info_by_user_id_v_2('$userId','${UUID.randomUUID()}')"

}
