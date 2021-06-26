package com.kotlin.delivery.common.config

import com.kotlin.delivery.auth.jwt.JwtFilter
import com.kotlin.delivery.auth.service.JwtTokenService
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtSecurityConfig(

    private val tokenService: JwtTokenService
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(http: HttpSecurity) {
        http.addFilterBefore(JwtFilter(tokenService), UsernamePasswordAuthenticationFilter::class.java)
    }
}
