package com.kotlin.delivery.member.common.repository

import com.kotlin.delivery.member.common.dto.LoginResponse
import com.kotlin.delivery.member.common.entity.MemberSummary
import org.springframework.data.jpa.repository.JpaRepository

interface MemberSummaryRepository : JpaRepository<MemberSummary, Long> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): LoginResponse?
}
