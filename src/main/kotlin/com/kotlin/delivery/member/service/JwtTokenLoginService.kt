package com.kotlin.delivery.member.service

import com.kotlin.delivery.auth.jwt.JwtTokenProvider
import com.kotlin.delivery.member.dto.LoginRequest
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Service
class JwtTokenLoginService(

    private val authManagerBuilder: AuthenticationManagerBuilder,

    private val tokenProvider: JwtTokenProvider
) : LoginService {

    override fun login(req: LoginRequest): String {
        val authentication = authManagerBuilder.`object`.authenticate(req.toAuthToken())
        return tokenProvider.generateToken(authentication)
    }
}
