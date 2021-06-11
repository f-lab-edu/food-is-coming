package com.kotlin.delivery

import com.kotlin.delivery.model.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long>
