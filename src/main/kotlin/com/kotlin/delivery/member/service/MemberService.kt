package com.kotlin.delivery.member.service

import com.kotlin.delivery.member.dto.MemberDTO
import com.kotlin.delivery.member.repository.MemberRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(

    private val memberRepository: MemberRepository,

    private val passwordEncoder: PasswordEncoder
) {
    fun signUp(memberDTO: MemberDTO) {
        validateDuplicateEmail(memberDTO.email)
        val member = memberDTO.toMemberEntity(memberDTO, passwordEncoder.encode(memberDTO.password))
        memberRepository.save(member)
    }

    private fun validateDuplicateEmail(email: String) =
        if (memberRepository.existsByEmail(email)) throw DuplicateKeyException("동일한 이메일로 가입된 사용자가 이미 존재합니다.")
        else Unit
}
