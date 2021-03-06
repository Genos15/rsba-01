package com.rsba.usermicroservice.resolver.mutation

import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.interpector.aspect.LoginSecured
import com.rsba.usermicroservice.repository.UserRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import mu.KLogger
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserMutation(
    private val service: UserRepository,
    private val logger: KLogger,
) : GraphQLMutationResolver {

    @LoginSecured
    suspend fun createUser(input: CreateUserInput, environment: DataFetchingEnvironment): Optional<User> {
        logger.warn { "+-- UserMutation -> createUser" }
        return service.onInsert(input = input)
    }

    suspend fun createAdmin(input: CreateAdminInput, environment: DataFetchingEnvironment): Int {
        logger.warn { "+-- UserMutation -> createAdmin" }
        return service.onInsertAdminAndCompany(input = input)
    }

    @AdminSecured
    suspend fun inviteUsers(input: InviteUsersInput, environment: DataFetchingEnvironment): Int {
        logger.warn { "+-- UserMutation -> inviteUsers" }

        val wait1 = CoroutineScope(Dispatchers.IO).async {
            return@async service.onInviteUsers(input = input, environment = environment)
        }

        val wait2 = CoroutineScope(Dispatchers.IO).async {
            val count = 0
//            wait1.await()?.forEach { me ->
//                if (publisher.immediateInvitation(me)) count++
//            }
            return@async count
        }

        return wait2.await()
    }

    @AdminSecured
    suspend fun blockUsers(input: List<UUID>, environment: DataFetchingEnvironment): Int {
        logger.warn { "+-- UserMutation -> blockUsers" }
        return service.onBlockUsers(input = input, environment = environment)
    }

    @LoginSecured
    suspend fun loginUser(input: LoginUserInput, environment: DataFetchingEnvironment): Optional<LoginUserReturn> =
        try {
            logger.warn { "+-- UserMutation -> loginUser" }
            val saving = service.onLoginUser(input = input)
            if (saving.isPresent) {
                Optional.of(saving.get())
            } else {
                Optional.empty()
            }
        } catch (e: Exception) {
            throw e
        }

    @AdminSecured
    suspend fun logoutUser(environment: DataFetchingEnvironment): Optional<Int> =
        service.onLogOut(environment = environment)
}