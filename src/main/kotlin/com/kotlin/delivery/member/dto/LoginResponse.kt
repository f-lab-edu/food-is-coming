package com.kotlin.delivery.member.dto

import com.kotlin.delivery.member.entity.Member

interface LoginResponse {

    fun getEmail(): String?

    fun getPassword(): String?

    fun getType(): Member.Type?
}
