package com.kotlin.delivery.auth.jwt

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {

    override fun handle(
        req: HttpServletRequest,
        res: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        res.sendError(HttpServletResponse.SC_FORBIDDEN)
    }
}
