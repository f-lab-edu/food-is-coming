package com.kotlin.delivery.member.common.dto

import com.kotlin.delivery.member.common.util.MemberType
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class SignUpRequest(

    @field:Pattern(
        regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",
        message = "이메일 형식에 맞게 입력해주세요."
    )
    val email: String,

    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,20}",
        message = "비밀번호는 영문 대/소문자, 숫자, 특수문자를 적어도 하나 씩 포함해서 8 ~ 20자 이내로 입력해주세요."
    )
    val password: String,

    @field:Pattern(
        regexp = "^[0-9a-zA-Z가-힣]{3,10}$",
        message = "한글/영문 대소문자 혹은 숫자를 3 ~ 10자 내로 입력해주세요."
    )
    val nickname: String,

    @field:Pattern(
        regexp = "^\\d{3}-\\d{4}-\\d{4}\$",
        message = "휴대폰 번호를 형식에 맞게 입력해주세요."
    )
    val mobile: String
)
