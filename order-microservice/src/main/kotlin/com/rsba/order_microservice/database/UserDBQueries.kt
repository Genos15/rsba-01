package  com.rsba.order_microservice.database


import java.util.*

object UserDBQueries {

    fun myPersonalInfo(input: UUID, token: UUID) =
        "SELECT on_retrieve_personal_info_by_user_id_v_2('$input', '$token')"

    fun myContacts(input: UUID, token: UUID) =
        "SELECT on_retrieve_contacts_info_by_user_id_v_2('$input', '$token')"
}
