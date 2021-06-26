package com.kotlin.delivery.auth.jwt

import com.kotlin.delivery.auth.service.JwtTokenService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val TOKEN_HEADER = "Authorization"

const val TOKEN_PREFIX = "Bearer "

class JwtFilter(private val tokenService: JwtTokenService) : OncePerRequestFilter() {

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        resolveToken(req)?.let {
            val authentication = tokenService.createAuthenticationToken(tokenService.parseToken(it))
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain.doFilter(req, res)
    }

    private fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader(TOKEN_HEADER)
        return bearerToken?.let {
            if (it.startsWith(TOKEN_PREFIX)) it.substring(7)
            else null
        }
    }
}
