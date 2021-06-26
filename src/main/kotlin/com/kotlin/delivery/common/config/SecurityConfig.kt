package com.kotlin.delivery.common.config

import com.kotlin.delivery.auth.jwt.JwtAccessDeniedHandler
import com.kotlin.delivery.auth.jwt.JwtAuthenticationEntryPoint
import com.kotlin.delivery.auth.service.JwtTokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(

    private val authEntryPoint: JwtAuthenticationEntryPoint,

    private val accessDeniedHandler: JwtAccessDeniedHandler,

    private val tokenService: JwtTokenService
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(authEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
            .and()
            .authorizeRequests()
            .antMatchers("/members/common/sign-up").permitAll()
            .antMatchers("/members/common/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(JwtSecurityConfig(tokenService))
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        val encoder = "bcrypt"
        val encoders = mutableMapOf<String, PasswordEncoder>()
        encoders[encoder] = BCryptPasswordEncoder(4)
        return DelegatingPasswordEncoder(encoder, encoders)
    }
}
