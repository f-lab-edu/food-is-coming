package com.kotlin.delivery.member.common

import com.kotlin.delivery.member.common.dto.LoginRequest
import com.kotlin.delivery.member.common.service.LoginService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members/common")
class MemberLoginController(private val loginService: LoginService) {

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest) = loginService.login(req)
}
