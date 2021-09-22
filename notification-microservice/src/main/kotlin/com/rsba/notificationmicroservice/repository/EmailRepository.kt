package com.rsba.notificationmicroservice.repository

import com.rsba.notificationmicroservice.domain.SingleInviteUserReturn
import reactor.core.publisher.Flux
import java.util.*
import javax.mail.Session

abstract class EmailRepository(val userName: String, val password: String) {
    abstract fun emailSession(): Session
    abstract fun emailProperties(): Properties
    abstract fun callbackUri(code: String, email: String, uri: String): String
    abstract fun onInviteUser(email: String, company: String): Flux<Unit>
    abstract fun onCreateAdmin(email: String, company: String, code: String): Flux<Unit>
    abstract fun callbackInviteUri(company: String, email: String, uri: String): String
    abstract fun onSendCreateInvitation(input: SingleInviteUserReturn?):Boolean
}