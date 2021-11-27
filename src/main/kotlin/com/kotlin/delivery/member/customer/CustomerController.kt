package com.kotlin.delivery.member.customer

import com.kotlin.delivery.member.common.dto.LoginRequest
import com.kotlin.delivery.member.common.dto.SignUpRequest
import com.kotlin.delivery.member.common.service.LoginService
import com.kotlin.delivery.member.common.util.MemberType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(

    private val customerSignupService: CustomerSignupService,

    private val loginService: LoginService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    fun signUp(@RequestBody req: SignUpRequest) = customerSignupService.signUp(req, MemberType.CUSTOMER, CustomerRepository::class)

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest) = loginService.login(req)
}
