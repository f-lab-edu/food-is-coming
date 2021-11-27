package com.kotlin.delivery.common.config

import com.kotlin.delivery.auth.jwt.JwtAccessDeniedHandler
import com.kotlin.delivery.auth.jwt.JwtAuthenticationEntryPoint
import com.kotlin.delivery.auth.service.JwtTokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity
class SecurityConfig(

    private val jwtAuthEntryPoint: JwtAuthenticationEntryPoint,

    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,

    private val tokenService: JwtTokenService
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
        /*
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
        */
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/**/sign-up").permitAll()
            .antMatchers("/customers/login").permitAll()
            .antMatchers("/members/common/my-info").hasAuthority("MEMBER")
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

    @Bean
    fun roleHierarchy() = RoleHierarchyImpl().apply {
        this.setHierarchy("CUSTOMER > MEMBER\nRIDER > MEMBER\nOWNER > MEMBER")
    }
}
