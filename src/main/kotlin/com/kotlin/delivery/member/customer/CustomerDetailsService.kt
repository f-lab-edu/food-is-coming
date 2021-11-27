package com.kotlin.delivery.member.customer

import com.kotlin.delivery.member.common.util.createUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

// TODO: 이대로는 모든 Service 가 여기를 보게된다. (even Rider, and Owner) 방안을 찾을 필요가 있다.
@Service
class CustomerDetailsService(private val customerRepository: CustomerRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails =
        customerRepository.findByEmail(email)?.let { createUserDetails(it) }
            ?: throw UsernameNotFoundException("사용자 정보가 일치하지 않습니다. 입력하신 정보를 다시 한 번 확인해주세요.")
}
