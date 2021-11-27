package com.kotlin.delivery.member.owner

import com.kotlin.delivery.member.common.dto.SignUpRequest
import com.kotlin.delivery.member.common.util.MemberType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/owners")
class OwnerController(private val ownerService: OwnerService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    fun signUp(@RequestBody req: SignUpRequest) = ownerService.signUp(req, MemberType.OWNER, OwnerRepository::class)
}
