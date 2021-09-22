package com.rsba.notificationmicroservice.controller

import com.rsba.notificationmicroservice.domain.SingleInviteUserReturn
import com.rsba.notificationmicroservice.service.EmailService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.io.IOException
import kotlin.jvm.Throws

import javax.mail.*

import javax.mail.internet.*
import org.springframework.core.env.Environment


@RestController
@RequestMapping("email")
@CrossOrigin(origins = ["*"])
class EmailController(private val service: EmailService, private val env: Environment) {

    @GetMapping("/confirm")
    @Throws(MessagingException::class, IOException::class, AddressException::class)
    fun createAdmin(
        @RequestParam("email") email: String,
        @RequestParam("code") code: String,
        @RequestParam("company") company: String
    ): Flux<Unit> = service.onCreateAdmin(email = email, company = company, code = code)

    @GetMapping("/invite")
    fun inviteUser(
        @RequestParam("email") email: String,
        @RequestParam("company") company: String
    ): Flux<Unit> = service.onInviteUser(email = email, company = company)

    @PostMapping("/invite-user")
    fun inviteUserEmail(@RequestBody instance: SingleInviteUserReturn): Boolean =
        service.onSendCreateInvitation(input = instance)


}