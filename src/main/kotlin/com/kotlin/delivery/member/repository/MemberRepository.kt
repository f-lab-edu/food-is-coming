package com.kotlin.delivery.member.repository

import com.kotlin.delivery.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {

    fun existsByEmail(email: String): Boolean
}
