package com.rsba.usermicroservice.service.implementation.users

import com.rsba.usermicroservice.context.token.TokenManagerImpl
import com.rsba.usermicroservice.domain.input.EditUserByMasterInput
import com.rsba.usermicroservice.domain.input.EditUserInput
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.query.database.UserDBHandler
import com.rsba.usermicroservice.query.database.UserDBQueries
import com.rsba.usermicroservice.repository.PhotoRepository
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import javax.servlet.http.Part
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface EditUserProfileImpl {

    suspend fun performEditUserProfile(
        database: DatabaseClient,
        input: EditUserInput,
        fileManager: PhotoRepository,
        environment: DataFetchingEnvironment
    ): Optional<User> = database.sql(
        UserDBQueries.editUserProfile(
            input = input,
            token = TokenManagerImpl.read(environment = environment)
        )
    ).map { row -> UserDBHandler.one(row = row) }
        .first()
        .onErrorResume {
            println("performEditUserProfile->error=${it.message}")
            throw it
        }
        .awaitFirstOrElse { Optional.empty() }

    suspend fun performEditUserPhoto(
        database: DatabaseClient,
        input: EditUserInput,
        fileManager: PhotoRepository,
        part: Part,
        environment: DataFetchingEnvironment
    ): Optional<User> = fileManager.edit(environment = environment, part = part)
        .flatMap {
            database.sql(
                UserDBQueries.editUserProfile(
                    input = input.apply { photo = it.orElse(null) },
                    token = TokenManagerImpl.read(environment = environment)
                )
            ).map { row -> UserDBHandler.one(row = row) }.first()
        }
        .onErrorResume {
            println("performEditPhoto->>error=${it.message}")
            throw it
        }
        .awaitFirstOrElse { Optional.empty() }

    suspend fun fnDeleteUserPhoto(
        database: DatabaseClient,
        input: UUID,
        fileManager: PhotoRepository,
        token: UUID
    ): Optional<User> = fileManager.delete(input = input, token = UUID.randomUUID())
        .flatMap {
            database.sql(UserDBQueries.removeUserPhotoByPhotoId(input = input, token = token))
                .map { row -> UserDBHandler.one(row = row) }.first()
        }
        .onErrorResume {
            println("fnDeleteUserPhoto=${it.message}")
            throw it
        }
        .awaitFirstOrElse { Optional.empty() }

    suspend fun fnEditUserByMaster(
        database: DatabaseClient,
        input: EditUserByMasterInput,
        token: UUID
    ): Optional<User> = database.sql(UserDBQueries.editUserByMasterId(input = input, token = token))
        .map { row -> UserDBHandler.one(row = row) }.first()
        .onErrorResume {
            println("fnEditUserByMaster=${it.message}")
            throw it
        }
        .awaitFirstOrElse { Optional.empty() }

}