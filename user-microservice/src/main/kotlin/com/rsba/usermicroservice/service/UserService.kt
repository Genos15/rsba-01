package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.configuration.logging.EmailHelper
import com.rsba.usermicroservice.context.CustomGraphQLContext
import com.rsba.usermicroservice.domain.input.*
import com.rsba.usermicroservice.domain.model.CachedUserContact
import com.rsba.usermicroservice.domain.model.CreateUserDatabaseParam
import com.rsba.usermicroservice.domain.model.Group
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.query.database.*
import com.rsba.usermicroservice.repository.UserRepository
import com.rsba.usermicroservice.utils.CacheHelper
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import reactor.kotlin.core.publisher.toMono
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest


@Service
class UserService(
    private val logger: KLogger,
    private val database: DatabaseClient,
    private val emailHelper: EmailHelper,
    private val cached: CacheService,
    private val queryHelper: UserDatabaseQuery,
    private val dataHandler: User2DataHandler
) : UserRepository {


    @Throws(RuntimeException::class)
    override suspend fun onInsert(input: CreateUserInput): Optional<User> {
        logger.warn { "+-- UserService -> onInsert" }

        if (!emailHelper.isValid(input.email)) {
            throw RuntimeException("НЕВЕРНЫЙ АДРЕС ЭЛЕКТРОННОЙ ПОЧТЫ")
        }

        return cached.getAsync(
            key = input.code,
            _class = SingleInviteUserReturn::class.java,
            source = CacheHelper.EMAIL_CACHE_NAME
        ).handle { single: Optional<SingleInviteUserReturn>, sink: SynchronousSink<SingleInviteUserReturn> ->
            logger.warn { "CACHED OUT = ${single.get()}" }
            if (single.isPresent) {
                sink.next(single.get())
            } else {
                sink.error(RuntimeException("НЕВОЗМОЖНО НАЙТИ СОВПАДЕНИЕ КОДА В ПАМЯТИ"))
            }
        }.flatMap {
            val instance = CreateUserDatabaseParam(
                userId = it.userId,
                companyId = it.companyId,
                groupId = it.groupId,
                roleId = it.roleId,
                firstName = input.firstName,
                lastName = input.lastName,
                middleName = input.middleName,
                login = input.login,
                password = input.password,
                phone = input.phone,
                lang = input.lang,
                email = it.email
            )
            database.sql(UserDBQueries.onInsertQueryFrom(input = instance))
                .map { row, meta -> UserDatabaseHandler.read(row = row, meta = meta) }
                .first()
        }.doOnNext {
            logger.warn { "USER = ${it.get()}" }
        }
            .awaitFirstOrElse { Optional.empty() }

    }

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
        val h1 = request.getHeader("Authorization")?.toLowerCase()
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
            .map { row, meta -> UserDBHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { it.message }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }
}