package com.rsba.usermicroservice.repository

import com.rsba.usermicroservice.domain.input.SingleInviteUserReturn
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "notification-microservice", url = "#{environment.NOTIFICATION_HOST}")
interface NotificationRepository {

    @PostMapping("/email/invite-user")
    fun inviteUserEmail(@RequestBody instance: SingleInviteUserReturn): Boolean

}