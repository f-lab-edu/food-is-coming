package com.kotlin.delivery.auth.service

import com.kotlin.delivery.auth.dao.AuthRepository
import com.kotlin.delivery.common.exception.AuthNotFoundException
import com.kotlin.delivery.common.properties.SMSAuthProperties
import net.nurigo.java_sdk.api.Message
import org.apache.commons.lang.RandomStringUtils
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("prod")
@Service
class SmsAuthService(

    private val authRepository: AuthRepository,

    private val prop: SMSAuthProperties
) : AuthService {

    override fun sendAuth(to: String) {
        val auth = RandomStringUtils.randomNumeric(6)
        val sms = Message(prop.apiKey, prop.secret)
        sms.send(createSMS(to, auth))

        authRepository.insert(to, auth)
    }

    override fun verifyAuth(to: String, inputAuth: String) =
        authRepository.select(to).run {
            val message = "인증정보가 일치하지 않습니다. 인증번호를 다시 확인해주세요."
            requireNotNull(this) {
                message
            }

            if (this != inputAuth) {
                throw AuthNotFoundException(message)
            }
        }.also {
            authRepository.delete(to)
        }

    private fun createSMS(mobile: String, auth: String) = hashMapOf(
        "from" to prop.sender,
        "to" to mobile,
        "text" to "[Food-Is-Coming] 회원가입을 계속 진행하시려면 인증번호 [$auth] 를 입력해주세요.",
        "type" to prop.type
    )
}
