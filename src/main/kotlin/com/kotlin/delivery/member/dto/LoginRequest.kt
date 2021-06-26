package com.kotlin.delivery.member.dto

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class LoginRequest(val email: String, val password: String) {

    fun toAuthToken() = UsernamePasswordAuthenticationToken(email, password)
}
