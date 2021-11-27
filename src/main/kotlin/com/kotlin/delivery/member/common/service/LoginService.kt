package com.kotlin.delivery.member.common.service

import com.kotlin.delivery.auth.dto.TokenSet
import com.kotlin.delivery.member.common.dto.LoginRequest

interface LoginService {

    fun login(req: LoginRequest): TokenSet
}
