package com.rsba.usermicroservice.service.implementation.users

import com.rsba.usermicroservice.context.token.TokenManagerImpl
import com.rsba.usermicroservice.domain.input.EditUserInput
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.query.database.UserDBHandler
import com.rsba.usermicroservice.query.database.UserDBQueries
import com.rsba.usermicroservice.repository.PhotoRepository
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface EditUserProfileImpl {

    suspend fun performEditUserProfile(
        database: DatabaseClient,
        input: EditUserInput,
        fileManager: PhotoRepository,
        environment: DataFetchingEnvironment
    ): Optional<User> = fileManager.addPhoto(environment = environment)
        .flatMap {
            database.sql(
                UserDBQueries.editUserProfile(
                    input = input.apply { photo = it.orElse(null) },
                    token = TokenManagerImpl.read(environment = environment)
                )
            ).map { row -> UserDBHandler.one(row = row) }.first()
        }
        .onErrorResume {
            println("performEditUserProfile->error=${it.message}")
            throw it
        }
        .awaitFirstOrElse { Optional.empty() }

}