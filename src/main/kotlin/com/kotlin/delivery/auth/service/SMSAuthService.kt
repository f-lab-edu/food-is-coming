package com.kotlin.delivery.auth.service

import com.kotlin.delivery.auth.dao.AuthRepository
import com.kotlin.delivery.common.exception.AuthNotFoundException
import com.kotlin.delivery.common.property.SMSAuthProperty
import net.nurigo.java_sdk.api.Message
import org.apache.commons.lang.RandomStringUtils
import org.springframework.stereotype.Service

@Service
class SMSAuthService(

    private val authRepository: AuthRepository,

    private val prop: SMSAuthProperty

) : AuthService {

    override fun sendAuth(mobile: String) {
        val auth = RandomStringUtils.randomNumeric(6)
        val sms = Message(prop.apiKey, prop.secret)
        sms.send(createSMS(mobile, auth))

        authRepository.insertAuth(mobile, auth)
    }

    override fun verifyAuth(mobile: String, auth: String) {
        val saved = authRepository.searchAuth(mobile, auth)
        if (saved != auth) throw AuthNotFoundException("인증정보가 일치하지 않습니다. 인증번호를 다시 확인해주세요.")
        authRepository.deleteAuth(mobile)
    }

    private fun createSMS(mobile: String, auth: String) = hashMapOf(
        "from" to prop.sender,
        "to" to mobile,
        "text" to "[Food-Is-Coming] 회원가입을 계속 진행하시려면 인증번호 [$auth] 를 입력해주세요.",
        "type" to prop.type
    )
}
