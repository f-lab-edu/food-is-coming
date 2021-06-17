package com.kotlin.delivery.member.service

import com.kotlin.delivery.member.dto.MemberDTO
import com.kotlin.delivery.member.entity.Member
import com.kotlin.delivery.member.repository.MemberRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.crypto.password.PasswordEncoder
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator

@DisplayName("회원가입 로직에 대한 서비스 계층을 테스트합니다.")
@ExtendWith(MockitoExtension::class)
internal class SignUpServiceTest {

    @InjectMocks
    lateinit var memberService: MemberService

    @Mock
    lateinit var memberRepository: MemberRepository

    @Mock
    lateinit var passwordEncoder: PasswordEncoder

    lateinit var fixture: MemberDTO

    @BeforeEach
    fun createMemberDTO() {
        fixture = MemberDTO(
            email = "test@email.com",
            password = "!@Soo#$1",
            nickname = "배달Master1",
            mobile = "010-0000-0000",
            Member.Type.RIDER
        )
    }

    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    private fun createEmailVariableMember(email: String) = MemberDTO(
        email = email,
        password = "!@Soo#$1",
        nickname = "배달Master1",
        mobile = "010-0000-0000",
        Member.Type.RIDER
    )

    private fun createPasswordVariableMember(password: String) = MemberDTO(
        email = "test@email.com",
        password = password,
        nickname = "배달Master1",
        mobile = "010-0000-0000",
        Member.Type.RIDER
    )

    private fun createNicknameVariableMember(nickname: String) = MemberDTO(
        email = "test@email.com",
        password = "!@Soo#$1",
        nickname = nickname,
        mobile = "010-0000-0000",
        Member.Type.RIDER
    )

    private fun createMobileVariableMember(mobile: String) = MemberDTO(
        email = "test@email.com",
        password = "!@Soo#$1",
        nickname = "배달Master1",
        mobile = mobile,
        Member.Type.RIDER
    )

    @Test
    @DisplayName("중복된 이메일이 발견되지 않는 경우 회원가입에 성공합니다.")
    fun `member sign up success when no duplicate email found`() {
        // given
        given(memberRepository.existsByEmail(fixture.email)).willReturn(false)

        val encodedPassword = "encodedPassword"
        given(passwordEncoder.encode(fixture.password)).willReturn(encodedPassword)
        val member = fixture.toMemberEntity(fixture, encodedPassword)

        given(memberRepository.save(member)).willReturn(member)

        // when
        memberService.signUp(memberDTO = fixture)

        // then
        verify(memberRepository, times(1)).existsByEmail(fixture.email)
        verify(passwordEncoder, times(1)).encode(fixture.password)
        verify(memberRepository, times(1)).save(member)
    }

    @Test
    @DisplayName("중복된 이메일이 발견되는 경우, 회원가입에 실패하며 DuplicateKeyException 이 발생합니다.")
    fun `member sign up fail with duplicate email found`() {
        // given
        given(memberRepository.existsByEmail(fixture.email)).willReturn(true)

        // when
        assertThrows<DuplicateKeyException> { memberService.signUp(memberDTO = fixture) }

        // then
        verify(memberRepository, times(1)).existsByEmail(fixture.email)
        verify(passwordEncoder, never()).encode(fixture.password)
        verify(memberRepository, never()).save(any())
    }

    /*
        Email Validation Test
     */
    @Test
    @DisplayName("이메일이 정상적인 형식(ex. test@email.com)을 따르고 있으면 validation 검증을 성공적으로 통과합니다.")
    fun `email validation success with valid email structure`() {
        val violations = validator.validate(createEmailVariableMember("test@email.com"))
        assertTrue(violations.isEmpty())
    }

    @Test
    @DisplayName("이메일 @ 앞 부분에 아무런 값도 존재하지 않는 경우 validation 위반이 발생합니다.")
    fun `email validation fail with no value before @`() {
        val violations = validator.validate(createEmailVariableMember("@email.com"))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("이메일 형식에 맞게 입력해주세요."))
    }

    @Test
    @DisplayName("이메일 @ 뒷 부분과 . 사이에 아무런 값도 존재하지 않는 경우 validation 위반이 발생합니다.")
    fun `email validation fail with no value between @ and dot`() {
        val violations = validator.validate(createEmailVariableMember("test@.com"))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("이메일 형식에 맞게 입력해주세요."))
    }

    @Test
    @DisplayName("이메일 . 뒷 부분에 아무런 값도 존재하지 않는 경우 validation 위반이 발생합니다.")
    fun `email validation fail with no value after dot`() {
        val violations = validator.validate(createEmailVariableMember("test@email."))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("이메일 형식에 맞게 입력해주세요."))
    }

    @Test
    @DisplayName("이메일에 제대로 된 값이 아닌 빈 문자열(\"\")이 입력된 경우 validation 위반이 발생합니다.")
    fun `email validation fail with empty string as input`() {
        val violations = validator.validate(createEmailVariableMember(""))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("이메일 형식에 맞게 입력해주세요."))
    }

    /*
        Password Validation Test
     */
    @Test
    @DisplayName("비밀번호가 정상적인 형식(대/소문자, 숫자, 특수문자를 각각 적어도 하나 씩 포함 해서 8 ~ 20자 이내)을 따르고 있으면 validation 검증을 성공적으로 통과합니다.")
    fun `password validation success with valid password structure`() {
        val violations = validator.validate(createPasswordVariableMember("!@Soo#$1"))
        assertTrue(violations.isEmpty())
    }

    @Test
    @DisplayName("비밀번호가 최소 제한 길이(7)에 못미치는 경우 validation 위반이 발생합니다.")
    fun `password validation fail with password shorter than minimum bound`() {
        val password = "!@So#$1"
        assertEquals(password.length, 7)

        val violations = validator.validate(createPasswordVariableMember(password))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("비밀번호는 영문 대/소문자, 숫자, 특수문자를 적어도 하나 씩 포함해서 8 ~ 20자 이내로 입력해주세요."))
    }

    @Test
    @DisplayName("비밀번호가 최대 제한 길이(20)를 벗어나는 경우 validation 위반이 발생합니다.")
    fun `password validation fail with password longer than maximum bound`() {
        val password = "!@So#$1!@So#1!@So#1@#"
        assertEquals(password.length, 21)

        val violations = validator.validate(createPasswordVariableMember(password))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("비밀번호는 영문 대/소문자, 숫자, 특수문자를 적어도 하나 씩 포함해서 8 ~ 20자 이내로 입력해주세요."))
    }

    @Test
    @DisplayName("비밀번호가 알파벳 대문자를 하나도 포함하고 있지 않은 경우 validation 위반이 발생합니다.")
    fun `password validation fail with password having no UPPERCASE letter`() {
        val violations = validator.validate(createPasswordVariableMember("!@Soo#$1".toLowerCase()))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("비밀번호는 영문 대/소문자, 숫자, 특수문자를 적어도 하나 씩 포함해서 8 ~ 20자 이내로 입력해주세요."))
    }

    @Test
    @DisplayName("비밀번호가 알파벳 소문자를 하나도 포함하고 있지 않은 경우 validation 위반이 발생합니다.")
    fun `password validation fail with password having no lowercase letter`() {
        val violations = validator.validate(createPasswordVariableMember("!@Soo#$1".toUpperCase()))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("비밀번호는 영문 대/소문자, 숫자, 특수문자를 적어도 하나 씩 포함해서 8 ~ 20자 이내로 입력해주세요."))
    }

    @Test
    @DisplayName("비밀번호가 숫자를 하나도 포함하고 있지 않은 경우 validation 위반이 발생합니다.")
    fun `password validation fail with password having no number`() {
        val violations = validator.validate(createPasswordVariableMember("!@Soo#$!"))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("비밀번호는 영문 대/소문자, 숫자, 특수문자를 적어도 하나 씩 포함해서 8 ~ 20자 이내로 입력해주세요."))
    }

    @Test
    @DisplayName("비밀번호가 특수문자를 하나도 포함하고 있지 않은 경우 validation 위반이 발생합니다.")
    fun `password validation fail with password having no special chars`() {
        val violations = validator.validate(createPasswordVariableMember("12Soo123"))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("비밀번호는 영문 대/소문자, 숫자, 특수문자를 적어도 하나 씩 포함해서 8 ~ 20자 이내로 입력해주세요."))
    }

    /*
        Nickname Validation Test
     */
    @Test
    @DisplayName("별명이 정상적인 형식(한글, 영문 소/대문자 혹은 숫자 포함 3 ~ 10자 이내)을 따르고 있으면 validation 검증을 성공적으로 통과합니다.")
    fun `nickname validation success with valid nickname structure`() {
        val violations = validator.validate(createNicknameVariableMember("배달Master1"))
        assertTrue(violations.isEmpty())
    }

    @Test
    @DisplayName("별명이 최소 제한 길이(2)에 못미치는 경우 validation 위반이 발생합니다.")
    fun `nickname validation fail with nickname shorter than minimum bound`() {
        val nickname = "배달"
        assertEquals(nickname.length, 2)

        val violations = validator.validate(createNicknameVariableMember(nickname))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("한글/영문 대소문자 혹은 숫자를 3 ~ 10자 내로 입력해주세요."))
    }

    @Test
    @DisplayName("별명이 최대 제한 길이(10)을 넘어가는 경우 validation 위반이 발생합니다.")
    fun `nickname validation fail with nickname longer than maximum bound`() {
        val nickname = "배달Master123"
        assertEquals(nickname.length, 11)

        val violations = validator.validate(createNicknameVariableMember(nickname))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("한글/영문 대소문자 혹은 숫자를 3 ~ 10자 내로 입력해주세요."))
    }

    @Test
    @DisplayName("별명에 공백 문자가 포함된 경우 validation 위반이 발생합니다.")
    fun `nickname validation fail with empty string included`() {
        val violations = validator.validate(createNicknameVariableMember("배달 배달"))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("한글/영문 대소문자 혹은 숫자를 3 ~ 10자 내로 입력해주세요."))
    }

    @Test
    @DisplayName("별명에 특수문자가 포함된 경우 validation 위반이 발생합니다.")
    fun `nickname validation fail with any special chars included`() {
        val violations = validator.validate(createNicknameVariableMember("배달Mas!@#"))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("한글/영문 대소문자 혹은 숫자를 3 ~ 10자 내로 입력해주세요."))
    }

    @Test
    @DisplayName("휴대펀 번호가 정상적인 형식(000-0000-0000)을 따르고 있으면 validation 검증을 성공적으로 통과합니다.")
    fun `mobile validation success with valid mobile structure`() {
        val violations = validator.validate(createMobileVariableMember("010-1234-5678"))
        assertTrue(violations.isEmpty())
    }

    /*
        mobile Validation Test
     */
    @Test
    @DisplayName("휴대펀 번호가 정상적인 형식(000-0000-0000)을 따르고 있지 않으면 validation 위반이 발생합니다.")
    fun `mobile validation fail with non-valid mobile structure`() {
        val violations = validator.validate(createMobileVariableMember("010-123-456"))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("휴대폰 번호를 형식에 맞게 입력해주세요."))
    }

    @Test
    @DisplayName("휴대펀 번호에 공백문자가 입력되면 validation 위반이 발생합니다.")
    fun `mobile validation fail with empty string as input`() {
        val violations = validator.validate(createMobileVariableMember(""))
        assertEquals(1, violations.size)
        assertThat(violations).extracting(ConstraintViolation<MemberDTO>::getMessage)
            .containsOnly(Tuple("휴대폰 번호를 형식에 맞게 입력해주세요."))
    }
}
