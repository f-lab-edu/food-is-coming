package com.kotlin.delivery.member.owner

import com.kotlin.delivery.member.common.util.createUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class OwnerDetailsService(private val ownerRepository: OwnerRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails =
        ownerRepository.findByEmail(email)?.let { createUserDetails(it) }
            ?: throw UsernameNotFoundException("사용자 정보가 일치하지 않습니다. 입력하신 정보를 다시 한 번 확인해주세요.")
}
