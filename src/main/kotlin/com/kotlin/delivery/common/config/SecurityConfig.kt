package com.kotlin.delivery.common.config

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
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/members/common/sign-up").permitAll()
            .antMatchers("/members/common/login").permitAll()
            .anyRequest().authenticated()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        val encoder = "bcrypt"
        val encoders = mutableMapOf<String, PasswordEncoder>()
        encoders[encoder] = BCryptPasswordEncoder(4)
        return DelegatingPasswordEncoder(encoder, encoders)
    }
}
