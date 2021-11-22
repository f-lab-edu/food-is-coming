package com.kotlin.delivery.auth.service

import com.kotlin.delivery.auth.dao.AuthRepository
import com.kotlin.delivery.common.exception.AuthNotFoundException
import com.kotlin.delivery.common.properties.SMSAuthProperties
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.never
import org.mockito.BDDMockito.times
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("문자(SMS) 인증로직에 대한 서비스 계층을 테스트합니다.")
@ExtendWith(MockitoExtension::class)
internal class SmsAuthServiceTest {

    @InjectMocks
    lateinit var authService: SmsAuthService

    @Mock
    lateinit var authRepository: AuthRepository

    @Mock
    lateinit var prop: SMSAuthProperties

    private val mobile = "000-0000-0000"

    private val inputAuth = "123456"

    private val actualAuth = "123456"

    @Test
    @DisplayName("문자로 전송받은 인증번호를 올바르게 입력하면 인증에 성공합니다.")
    fun `verify auth number success with correct auth number input`() {
        // given
        given(authRepository.select(mobile)).willReturn(actualAuth)
        given(authRepository.delete(mobile)).willReturn(true)

        // when
        authService.verifyAuth(mobile, inputAuth)

        // then
        verify(authRepository, times(1)).select(mobile)
        verify(authRepository, times(1)).delete(mobile)
    }

    @Test
    @DisplayName("문자로 전송받은 인증번호와 다른 번호를 입력하면 인증에 실패하며 AuthNotFoundException 이 발생합니다.")
    fun `verify auth number fail with incorrect auth number input`() {
        // given
        given(authRepository.select(mobile)).willReturn("123123")

        // when
        assertThrows<AuthNotFoundException> { authService.verifyAuth(mobile, inputAuth) }

        // then
        verify(authRepository, times(1)).select(mobile)
        verify(authRepository, never()).delete(mobile)
    }

    // TODO: a test for when null from select (redis)
}
