package com.kotlin.delivery.auth.service

import com.kotlin.delivery.common.exception.AuthNotFoundException
import org.springframework.stereotype.Service

@Service
class MockSmsAuthService : AuthService {

    val mockAuth = "123456"

    override fun sendAuth(to: String) {
        return
    }

    override fun verifyAuth(to: String, inputAuth: String) {
        if (inputAuth != mockAuth) {
            throw AuthNotFoundException("인증정보가 일치하지 않습니다. 인증번호를 다시 확인해주세요.")
        }
    }
}
