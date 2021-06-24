package com.kotlin.delivery.member.controller

import com.kotlin.delivery.member.dto.LoginRequest
import com.kotlin.delivery.member.dto.MemberDTO
import com.kotlin.delivery.member.service.LoginService
import com.kotlin.delivery.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/members/common")
class MemberController(

    private val memberService: MemberService,

    private val loginService: LoginService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody memberDTO: MemberDTO) = memberService.signUp(memberDTO)

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest) = loginService.login(req)
}
