package com.rsba.usermicroservice.repository

import com.rsba.usermicroservice.domain.model.ContactInfo
import java.util.*

interface ContactInfoRepository {
    suspend fun onRetrieveByUserId(
        userIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<ContactInfo>>
}