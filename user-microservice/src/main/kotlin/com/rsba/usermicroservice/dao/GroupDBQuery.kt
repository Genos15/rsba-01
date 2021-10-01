package com.rsba.usermicroservice.dao

 import com.rsba.usermicroservice.domain.input.CreateOrEditGroupInput
 import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.util.*

@Component
class GroupDBQuery {

    fun onCreateOrEditGroup(input: CreateOrEditGroupInput, token: UUID) =
        "SELECT on_create_or_edit_group('${Json.encodeToString(input)}', '$token')"

}