package com.kotlin.delivery.member.service

import com.kotlin.delivery.member.dto.LoginResponse
import com.kotlin.delivery.member.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MemberDetailsService(private val memberRepository: MemberRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails =
        memberRepository.findByEmail(email)?.let { createUserDetails(it) }
            ?: throw UsernameNotFoundException("사용자 정보가 일치하지 않습니다. 입력하신 정보를 다시 한 번 확인해주세요.")

    private fun createUserDetails(res: LoginResponse): User {
        val grantedAuthority = SimpleGrantedAuthority(res.getType().toString())
        return User(res.getEmail(), res.getPassword(), setOf(grantedAuthority))
    }
}
