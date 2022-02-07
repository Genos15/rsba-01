package com.rsba.tasks_microservice.data.service.usecase.users


import com.rsba.tasks_microservice.data.dao.UserDao
import com.rsba.tasks_microservice.data.service.usecase.queries.TaskQueries
import com.rsba.tasks_microservice.domain.model.User
import com.rsba.tasks_microservice.domain.model.UserActivityLayer
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.custom.user.RetrieveUserActivityUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveUserActivityUseCaseImpl(private val database: DatabaseClient) : RetrieveUserActivityUseCase {

    override suspend fun invoke(
        first: Int,
        after: UUID?,
        layer: UserActivityLayer,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        token: UUID
    ): List<User> =
        database.sql(
            TaskQueries.userActivities(
                token = token,
                first = first,
                after = after,
                layer = layer,
                rangeStart = rangeStart,
                rangeEnd = rangeEnd,
            )
        ).map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? UserDao?)?.to } ?: emptyList() }
            .onErrorResume { throw it }
            .awaitFirstOrElse { emptyList() }
}