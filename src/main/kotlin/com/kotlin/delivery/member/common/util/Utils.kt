package com.kotlin.delivery.member.common.util

import com.kotlin.delivery.member.common.dto.LoginResponse
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

fun createUserDetails(res: LoginResponse): User {
    val grantedAuthority = SimpleGrantedAuthority(res.getType().toString())
    return User(res.getEmail(), res.getPassword(), setOf(grantedAuthority))
}
