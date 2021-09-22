package com.rsba.notificationmicroservice.service

import com.rsba.notificationmicroservice.domain.SingleInviteUserReturn
import com.rsba.notificationmicroservice.repository.EmailRepository
import com.rsba.notificationmicroservice.utils.EmailMessageHelper
import mu.KLogger
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.lang.StringBuilder
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

@Service
class EmailService(private val env: Environment, private val logger: KLogger) :
//    EmailRepository(userName = "info@eatmuse.com", password = "tyKmor-cagha5-depfiv") {
    EmailRepository(userName = "no-reply@tcn-nn.ru", password = "gQbamay2+hjt") {

    override fun emailSession(): Session = Session.getInstance(emailProperties(), object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(userName, password)
        }
    })

    override fun emailProperties(): Properties {
        val props = Properties()
//        props["mail.smtp.auth"] = "true"
//        props["mail.smtp.starttls.enable"] = "true"
//        props["mail.smtp.host"] = "smtp.ionos.com"
//        props["mail.smtp.port"] = "587"

        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = "smtp.jino.ru"
        props["mail.smtp.port"] = "587"
        return props
    }

    override fun callbackUri(code: String, email: String, uri: String): String = "$uri/email/confirm/$email/$code"

    override fun callbackInviteUri(company: String, email: String, uri: String): String =
        "$uri/email/invite?email=$email&company=$company"

    override fun onInviteUser(email: String, company: String): Flux<Unit> = try {
        val uriLocal = "${env.getProperty("spring.cloud.consul.host")}:9000"
        val userUri = "http://$uriLocal/user-microservice"
        val uri = userUri
        logger.warn { "USER-URI = $uri" }
        Flux.just(email)
            .map {
                logger.warn { "+------- building the email message" }
                val msg = MimeMessage(emailSession())
                msg.setFrom(InternetAddress("info@eatmuse.com", true))
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(it))
                msg.subject = "Подтвердите свой электронный адрес"
                msg.sentDate = Date()
                val messageBodyPart = MimeBodyPart()
                val content = EmailMessageHelper.inviteHtml(
                    uri = callbackInviteUri(company = company, email = email, uri = uri),
                    company = company
                )
                messageBodyPart.setContent(content, "text/html; charset=UTF-8")
                val multipart: Multipart = MimeMultipart()
                multipart.addBodyPart(messageBodyPart)
                msg.setContent(multipart)
                Transport.send(msg)
            }
    } catch (e: Exception) {
        logger.warn { "≠------ error = ${e.message}" }
        Flux.error { RuntimeException("НЕВОЗМОЖНО ПОЛУЧИТЬ ДОСТУП К ВАШИМ ЗАПИСЯМ. ПОЖАЛУЙСТА, ПОДОЖДИТЕ 5 МИНУТ И ПОПРОБУЙТЕ ЕЩЁ РАЗ") }
    }

    override fun onCreateAdmin(email: String, company: String, code: String): Flux<Unit> = try {
        val uriLocal = "${env.getProperty("spring.cloud.consul.host")}:9000"
        val userUri = "http://$uriLocal/user-microservice"
        val uri = userUri
        logger.warn { "USER-URI = $uri" }

        Flux.just(email)
            .doOnNext {
                logger.warn { "+------- building the email message" }
                val msg = MimeMessage(emailSession())
                msg.setFrom(InternetAddress("info@eatmuse.com", true))
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(it))
                msg.subject = "Подтвердите свой электронный адрес"
                msg.sentDate = Date()
                val messageBodyPart = MimeBodyPart()
                val content = EmailMessageHelper.html(
                    uri = callbackUri(code = code, email = email, uri = uri),
                    company = company
                )
                messageBodyPart.setContent(content, "text/html; charset=UTF-8")
                val multipart: Multipart = MimeMultipart()
                multipart.addBodyPart(messageBodyPart)
                msg.setContent(multipart)
                Transport.send(msg)
            }
            .map { logger.warn { "+-------- Sent the email…" } }

    } catch (e: Exception) {
        logger.error(e) { "We have an error $e" }
        logger.warn { "≠-------Error while sending the email…" }
        Flux.error(RuntimeException("Impossible to send message… Check email configurations"))
    }

    override fun onSendCreateInvitation(input: SingleInviteUserReturn?): Boolean = try {
        val uri: String? = try {
            val db = env.getRequiredProperty("PUBLIC_INVITATION_URL")
            db
        } catch (e: IllegalStateException) {
            logger.warn { "≠------ onSendCreateInvitation -> error= ${e.message}" }
            null
        } catch (e: NullPointerException) {
            logger.warn { "≠------ onSendCreateInvitation -> error= ${e.message}" }
            null
        }

        logger.warn { "URI = $uri" }

        if (uri != null) {
            logger.warn { "level 1" }
            val url = StringBuilder()
                .append(uri)
                .append("companyName=${input?.companyName}&")
                .append("roleName=${input?.roleName}&")
                .append("groupName=${input?.groupName ?: ""}&")
                .append("code=${input?.code}&")
                .append("email=${input?.email}&")
                .append("lang=${input?.lang ?: "ru"}")
                .toString().trim()
            logger.warn { "level 2" }

            val mine = MimeMessage(emailSession())
            mine.setFrom(InternetAddress(this.userName, true))
            logger.warn { "level 3" }

            mine.setRecipients(Message.RecipientType.TO, InternetAddress.parse(input?.email))
            logger.warn { "level 4" }

            mine.subject = "СОЗДАТЬ АККАУНТ"
            mine.sentDate = Date()
            logger.warn { "level 5" }

            val messageBodyPart = MimeBodyPart()
            val content = if (input?.lang.equals("ru", ignoreCase = true)) {
                EmailMessageHelper.inviteRuHtml(source = input, url = url)
            } else {
                EmailMessageHelper.inviteEnHtml(source = input, url = url)
            }
            logger.warn { "level 6" }

            messageBodyPart.setContent(content, "text/html; charset=UTF-8")
            val multipart: Multipart = MimeMultipart()
            multipart.addBodyPart(messageBodyPart)
            mine.setContent(multipart)
            Transport.send(mine)
            logger.warn { "level 7" }
        }
        true
    } catch (e: Exception) {
        logger.warn { "+onSendCreateInvitation -> error = ${e.message}" }
        false
    }
}