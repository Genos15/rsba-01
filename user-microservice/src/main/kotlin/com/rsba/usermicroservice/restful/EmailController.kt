package com.rsba.usermicroservice.restful

import com.rsba.usermicroservice.repository.UserRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("email")
@CrossOrigin(origins = ["*"])
class EmailController(private val service: UserRepository) {

    @GetMapping("/confirm/{email}/{code}")
    fun confirm(@PathVariable code: String, @PathVariable email: String): Mono<String> =
        service.onConfirmEmail(email = email, code = code)

    @GetMapping("/invite")
    fun invite(@RequestParam("company") company: String, @RequestParam("email") email: String): Mono<String> =
        Mono.just("ДАВАЙТЕ РАЗВЕРНЕМ ФРОНТЕНД-ПРИЛОЖЕНИЕ И ПОСМОТРИМ РЕЗУЛЬТАТ.")
}