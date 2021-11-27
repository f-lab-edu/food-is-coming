package com.kotlin.delivery.member.common.dto

import com.kotlin.delivery.member.common.util.MemberType

interface LoginResponse {

    fun getEmail(): String?

    fun getPassword(): String?

    fun getType(): MemberType?
}
