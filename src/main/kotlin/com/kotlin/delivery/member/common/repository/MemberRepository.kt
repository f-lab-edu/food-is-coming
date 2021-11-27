package com.kotlin.delivery.member.common.repository

import com.kotlin.delivery.common.entity.Member
import com.kotlin.delivery.member.common.dto.LoginResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface MemberRepository<T : Member, ID> : JpaRepository<T, ID> {

    fun findByEmail(email: String): LoginResponse?

    fun existsByEmail(email: String): Boolean
}
