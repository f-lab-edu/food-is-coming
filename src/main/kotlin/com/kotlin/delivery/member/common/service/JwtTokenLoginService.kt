package com.kotlin.delivery.member.common.service

import com.kotlin.delivery.auth.dto.TokenSet
import com.kotlin.delivery.auth.service.JwtTokenService
import com.kotlin.delivery.member.common.dto.LoginRequest
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Service
class JwtTokenLoginService(

    private val authManagerBuilder: AuthenticationManagerBuilder,

    private val tokenService: JwtTokenService
) : LoginService {

    override fun login(req: LoginRequest): TokenSet {
        val authentication = authManagerBuilder.`object`.authenticate(req.toAuthToken())

        return tokenService.createTokenSet(authentication).also {
            tokenService.saveRefreshToken(authentication.name, it.refreshToken)
        }
    }
}
