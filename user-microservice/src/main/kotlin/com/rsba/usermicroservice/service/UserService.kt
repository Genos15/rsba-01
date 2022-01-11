package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.configuration.logging.EmailHelper
import com.rsba.usermicroservice.context.CustomGraphQLContext
import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.CachedUserContact
import com.rsba.usermicroservice.domain.model.CreateUserDatabaseParam
import com.rsba.usermicroservice.domain.model.Group
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.query.database.*
import com.rsba.usermicroservice.repository.PhotoRepository
import com.rsba.usermicroservice.repository.UserRepository
import com.rsba.usermicroservice.service.implementation.users.EditUserProfileImpl
import com.rsba.usermicroservice.service.implementation.users.RetrievePhotoImpl
import com.rsba.usermicroservice.utils.CacheHelper
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactor.mono
import mu.KLogger
import javax.servlet.http.Part
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import reactor.kotlin.core.publisher.toMono
import java.io.InputStream
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest


@Service
class UserService(
    private val logger: KLogger,
    private val database: DatabaseClient,
    private val fileManager: PhotoRepository,
    private val emailHelper: EmailHelper,
    private val cached: CacheService,
    private val queryHelper: UserDatabaseQuery,
    private val dataHandler: User2DataHandler
) : UserRepository, EditUserProfileImpl, RetrievePhotoImpl {

    @Throws(RuntimeException::class)
    override suspend fun onInsert(input: CreateUserInput): Optional<User> =
        database.sql(UserDBQueries.onInsertQueryFrom(input = input))
            .map { row, meta -> UserDatabaseHandler.read(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun onInsertAdminAndCompany(input: CreateAdminInput): Int {
        logger.warn { "+-- UserService -> onInsertAdminAndCompany" }

        if (!emailHelper.isValid(input.email)) {
            throw RuntimeException("Недействительный адрес электронной почты")
        }
        return database.sql(UserDBQueries.onInsertAdminQueryFrom(input = input))
            .map { row, meta -> UserDatabaseHandler.readCreateAdminReturn(row = row, meta = meta) }
            .first()
            .handle { t: Optional<CreateAdminReturn>, sink: SynchronousSink<CreateAdminReturn> ->
                if (t.isPresent) {
                    sink.next(t.get())
                } else {
                    sink.error(RuntimeException("Невозможно вставить этого пользователя. Пожалуйста, повторите попытку позже"))
                }
            }
            .map {
                emailHelper.sendConfirmation(email = it.email, username = it.login, companyName = it.companyname)
                return@map 1
            }
            .awaitFirstOrElse { 0 }
    }

    override suspend fun onEdit(input: User, environment: DataFetchingEnvironment): Optional<User> {
        logger.warn { "+-- UserService -> onEdit" }
        TODO("Not yet implemented")
    }

    override suspend fun onDelete(input: User, environment: DataFetchingEnvironment): Int {
        logger.warn { "+-- UserService -> onDelete" }
        TODO("Not yet implemented")
    }

    override suspend fun onRetrieveOne(input: User, environment: DataFetchingEnvironment): Optional<User> {
        logger.warn { "+-- UserService -> onRetrieveOne" }
        TODO("Not yet implemented")
    }

    override suspend fun onRetrieveAll(first: Int, after: UUID?, token: UUID): List<User> =
        database.sql(UserDBQueries.onRetrieveAll(first = first, after = after, token = token))
            .map { row, meta -> UserDBHandler.all(row = row, meta = meta) }
            .first()
            .log()
            .awaitFirstOrElse { mutableListOf() }

    override fun onConfirmEmail(email: String, code: String): Mono<String> {
        logger.warn { "+-- UserService -> onConfirmEmail" }
        return Mono.just(Optional.ofNullable(emailHelper.verifyEmailFromCode(email = email, code = code)))
            .handle { t: Optional<CachedUserContact>, sink: SynchronousSink<CachedUserContact> ->
                if (t.isPresent) {
                    sink.next(t.get())
                } else {
                    sink.error(RuntimeException("Невозможно подтвердить адрес электронной почты. Пожалуйста, повторите попытку позже"))
                }
            }
            .flatMap {
                return@flatMap database.sql(UserDBQueries.onConfirmEmailQueryFrom(it))
                    .map { row, meta -> UserDatabaseHandler.count(row = row, meta = meta) }
                    .first()
            }
            .map {
                if (it == 0) {
                    logger.warn { "+-------- no new row inserted" }
                    return@map "Невозможно подтвердить адрес электронной почты. Пожалуйста, повторите попытку позже"
                } else {
                    logger.warn { "+-------- new rows = $it" }
                    return@map "Ваш адрес электронной почты успешно подтвержден"
                }
            }
            .onErrorResume { e ->
                logger.warn { "≠------ error = ${e.message}" }
                Mono.error { RuntimeException("Невозможно подтвердить адрес электронной почты. Пожалуйста, повторите попытку позже") }
            }
    }

    override suspend fun onInviteUsers(
        input: InviteUsersInput,
        environment: DataFetchingEnvironment
    ): List<SingleInviteUserReturn>? {
        logger.warn { "+-- UserService -> onInviteUsers" }
        return Flux.fromIterable(input.email)
            .filter { emailHelper.isValid(it) }
            .map { SingleInviteUsersInput(roleId = input.roleId, groupId = input.groupId, email = it) }
            .flatMap {
                database.sql(UserDBQueries.onInviteUserQueryFrom(it))
                    .map { row, meta -> UserDatabaseHandler.oneInvitationReturn(row = row, meta = meta) }
                    .first()
            }
            .handle { t: Optional<SingleInviteUserReturn>, sink: SynchronousSink<SingleInviteUserReturn> ->
                if (t.isPresent) {
                    sink.next(t.get())
                } else {
                    sink.complete()
                }
            }
            .collectList()
            .map {
                var headerLang = "ru"
                try {
                    val context: CustomGraphQLContext = environment.getContext()
                    val request: HttpServletRequest = context.httpServletRequest
                    headerLang = request.getHeader("X-Lang") ?: headerLang
                } catch (e: Exception) {
                    logger.warn { "error invite = ${e.message}" }
                }

                return@map it.map { me ->
                    me.lang = headerLang
                    me.message = input.message
                    //Cache the SingleInviteUserReturn
                    cached.saveInvitation(me)
                    //Send Message through Publisher
//                    publisher.immediateInvitation(me)
                    me
                }
            }
//            .map { counter.incrementAndGet() }
            .awaitFirstOrElse { listOf() }
    }

    override suspend fun onBlockUsers(input: List<UUID>, environment: DataFetchingEnvironment): Int {
        return database.sql(UserDBQueries.onBlockUsersQueryFrom(input = input))
            .map { row, meta -> UserDatabaseHandler.count(row = row, meta = meta) }
            .first()
            .handle { income: Int, sink: SynchronousSink<Int> ->
                if (income > 0) {
                    sink.next(income)
                } else {
                    sink.error(RuntimeException("Невозможно заблокировать пользователя(ов). Что-то пошло не так"))
                }
            }
            .awaitFirstOrElse { 0 }
    }

    override suspend fun onLoginUser(input: LoginUserInput): Optional<LoginUserReturn> {
        logger.warn { "+-- UserService -> onLoginUser" }

        return database.sql(UserDBQueries.onLoginUserQueryFrom(input = input))
            .map { row, meta -> UserDatabaseHandler.one(row = row, meta = meta) }
            .first()
            .handle { t: Optional<LoginUserReturn>, sink: SynchronousSink<Optional<LoginUserReturn>> ->
                logger.warn { "OUT = ${t.get()}" }
                if (t.isPresent) {
                    sink.next(t)
                } else {
                    sink.error(RuntimeException("Либо пользователь не существует в нашей системе, либо он не подтвердил контакт."))
                }
            }
            .flatMap {
                it.get().toJson().let { it1 ->
                    logger.warn { "+------ adding in cache" }
//                    publisher.immediateShare(it1)
                    logger.warn { "+------ added in cache" }
                }
                it.toMono()
            }.awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun onLogOut(environment: DataFetchingEnvironment): Optional<Int> {
        logger.warn { "+-- UserService -> onLogOut" }
        val context: CustomGraphQLContext = environment.getContext()
        val request: HttpServletRequest = context.httpServletRequest
        val h1 = request.getHeader("Authorization")?.lowercase()
        val h2 = h1?.replace("bearer ", "")
        return try {
            h2?.let {
                val input = UUID.fromString(it)
                return database.sql(queryHelper.onLogout(input = input))
                    .map { row, meta -> dataHandler.oneToken(row = row, meta = meta) }
                    .first()
                    .handle { source: Optional<LoginUserReturn>, sink: SynchronousSink<Optional<Int>> ->
                        if (source.isPresent) {
                            sink.next(Optional.of(1))
                        } else {
                            sink.error(RuntimeException("IMPOSSIBLE TO FIND THE REFERENCE USER TOKEN IN SYSTEM"))
                        }
                    }
                    .onErrorResume {
                        logger.warn { it.message }
                        throw it
                    }.awaitFirstOrElse { Optional.empty() }
            } ?: Optional.of(0)
        } catch (e: Exception) {
            Optional.of(0)
        }
    }

    override suspend fun onRetrieveNotInGroup(
        first: Int,
        after: UUID?,
        token: UUID
    ): List<User> = database.sql(UserDBQueries.onRetrieveNotInGroup(first = first, after = after, token = token))
        .map { row, meta -> UserDBHandler.all(row = row, meta = meta) }
        .first()
        .onErrorResume {
            logger.warn { it.message }
            throw it
        }
        .awaitFirstOrElse { mutableListOf() }

    override suspend fun onRetrieveUserInGroup(
        groupIds: Set<UUID>,
        groupId: UUID,
        first: Int,
        after: UUID?
    ): Map<UUID, List<User>> {
        logger.warn { "+-- UserService -> onRetrieveUserInGroup" }
        return Flux.fromIterable(groupIds)
            .flatMap { id ->
                database.sql(UserDBQueries.onRetrieveByGroupId(groupId = id, first = first, after = after))
                    .map { row, meta -> UserDatabaseHandler.all(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<UUID, List<User>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { it.message }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }
    }

    override suspend fun myGroups(ids: Set<UUID>, instanceId: UUID, first: Int, after: UUID?): Map<UUID, List<Group>> =
        Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(UserDBQueries.myGroups(userId = id))
                    .map { row, meta -> GroupDBHandler.all(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<UUID, List<Group>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { it.message }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun retrieveByToken(token: UUID): Optional<User> =
        database.sql(UserDBQueries.retrieveByToken(token = token))
            .map { row -> UserDBHandler.one(row = row) }
            .first()
            .onErrorResume {
                logger.warn { it.message }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    /**
     * @param input this object consist of different parameter that have to be edited in user instance.
     * @param environment the data wrapper GraphQL engine uses to keep request meta data.
     * @return {@link Optional<UUID>} url id of a saved file
     */
    override suspend fun editUserProfile(input: EditUserInput, environment: DataFetchingEnvironment): Optional<User> =
        performEditUserProfile(input = input, database = database, environment = environment, fileManager = fileManager)

    /**
     * @param input unique id of image ressource in database
     * @return {@link Mono<Image>}
     */
    override fun retrievePhoto(input: UUID): Mono<InputStream> =
        retrievePhotoFn(input = input, fileManager = fileManager)

    override suspend fun updatePhoto(input: Part, environment: DataFetchingEnvironment): Optional<User> =
        performEditUserPhoto(
            input = EditUserInput(),
            database = database,
            environment = environment,
            fileManager = fileManager,
            part = input
        )

    override suspend fun removePhoto(input: UUID, token: UUID): Optional<User> =
        fnDeleteUserPhoto(
            input = input,
            database = database,
            token = token,
            fileManager = fileManager,
        )

    override suspend fun editUserByMaster(input: EditUserByMasterInput, token: UUID): Optional<User> =
        fnEditUserByMaster(database = database, input = input, token = token)
}