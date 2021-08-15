package com.kotlin.delivery.auth.controller

import com.kotlin.delivery.auth.dto.TokenSet
import com.kotlin.delivery.auth.service.AuthService
import com.kotlin.delivery.auth.service.JwtTokenService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members/common")
class AuthController(private val authService: AuthService, private val tokenService: JwtTokenService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sms-auth")
    fun sendSMSAuth(@RequestParam mobile: String) = "Sent"

    @PostMapping("/sms-auth/verification")
    fun verifySMSAuth(@RequestParam mobile: String, @RequestParam auth: String) = "Verified"

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/token-reissue")
    fun reissueToken(@RequestBody tokenSet: TokenSet) = tokenService.reissueToken(tokenSet)
}
