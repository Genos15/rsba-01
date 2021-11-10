package  com.rsba.order_microservice.domain.repository

import com.rsba.order_microservice.domain.model.*
import java.util.*

interface WorklogRepository {

    suspend fun myActor(
        ids: Set<UUID>,
        userId: UUID,
        first: Int,
        after: UUID? = null
    ): Map<UUID, Optional<User>>

    suspend fun retrieveWorklogsByTaskId(taskId: UUID, first: Int, after: UUID?, token: UUID): List<Worklog>
}
