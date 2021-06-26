package com.kotlin.delivery.auth.jwt

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(req: HttpServletRequest, res: HttpServletResponse, authException: AuthenticationException) {
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED)
    }
}
