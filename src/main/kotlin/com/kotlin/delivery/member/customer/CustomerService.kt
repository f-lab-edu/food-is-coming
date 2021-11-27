package com.kotlin.delivery.member.customer

import com.kotlin.delivery.common.config.BeanConfig
import com.kotlin.delivery.member.common.entity.Member
import com.kotlin.delivery.member.common.entity.MemberSummary
import com.kotlin.delivery.member.common.service.MemberService
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class CustomerSignupService(val beanConfig: BeanConfig) : MemberService<Customer, CustomerRepository>() {

    override fun decideRepository(repositoryClass: KClass<CustomerRepository>) = beanConfig.getBean(repositoryClass)

    override fun createTypeEntity(summary: MemberSummary, common: Member) = Customer(summary, common)
}

