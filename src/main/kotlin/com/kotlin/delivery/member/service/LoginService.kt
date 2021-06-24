package com.kotlin.delivery.member.service

import com.kotlin.delivery.member.dto.LoginRequest

interface LoginService {

    fun login(req: LoginRequest): String
}
