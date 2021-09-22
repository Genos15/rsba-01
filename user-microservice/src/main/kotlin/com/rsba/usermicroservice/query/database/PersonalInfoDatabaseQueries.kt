package com.rsba.usermicroservice.query.database

import java.util.*

object PersonalInfoDatabaseQueries {

    fun onRetrieveByUserId(userId: UUID): String =
        "SELECT on_retrieve_personal_info_by_user_id_v_2('$userId','${UUID.randomUUID()}')"

}
