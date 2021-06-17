package com.kotlin.delivery.auth.controller

import com.kotlin.delivery.auth.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members/common")
class AuthController(private val authService: AuthService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sms-auth")
    fun sendSMSAuth(@RequestParam mobile: String) {
        authService.sendAuth(mobile)
    }

    @PostMapping("/sms-auth/verification")
    fun verifySMSAuth(@RequestParam mobile: String, @RequestParam auth: String) {
        authService.verifyAuth(mobile, auth)
    }
}
