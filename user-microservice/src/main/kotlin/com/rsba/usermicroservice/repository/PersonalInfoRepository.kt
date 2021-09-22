package com.rsba.usermicroservice.repository

import com.rsba.usermicroservice.domain.model.PersonalInfo
import java.util.*

interface PersonalInfoRepository {
    suspend fun onRetrieveByUserId(
        userIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, PersonalInfo?>
}