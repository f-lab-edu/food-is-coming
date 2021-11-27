package com.kotlin.delivery.member.owner

import com.kotlin.delivery.common.config.BeanConfig
import com.kotlin.delivery.member.common.entity.Member
import com.kotlin.delivery.member.common.entity.MemberSummary
import com.kotlin.delivery.member.common.service.MemberService
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class OwnerService(val beanConfig: BeanConfig) : MemberService<Owner, OwnerRepository>() {

    override fun decideRepository(repositoryClass: KClass<OwnerRepository>) = beanConfig.getBean(repositoryClass)

    override fun createTypeEntity(summary: MemberSummary, common: Member) = Owner(summary, common)
}
