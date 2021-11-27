package com.kotlin.delivery.member.common.repository

import com.kotlin.delivery.member.common.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface MemberRepository<T : Member, ID> : JpaRepository<T, ID>