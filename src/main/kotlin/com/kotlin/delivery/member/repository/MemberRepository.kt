package com.kotlin.delivery.member.repository

import com.kotlin.delivery.member.dto.LoginResponse
import com.kotlin.delivery.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByEmail(email: String): LoginResponse?

    fun existsByEmail(email: String): Boolean
}
