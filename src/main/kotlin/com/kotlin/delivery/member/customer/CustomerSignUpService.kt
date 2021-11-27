package com.kotlin.delivery.member.customer

import com.kotlin.delivery.common.config.BeanConfig
import com.kotlin.delivery.common.entity.Member
import com.kotlin.delivery.member.common.service.AbstractSignUpService
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class CustomerSignupService(val beanConfig: BeanConfig) : AbstractSignUpService<Customer, CustomerRepository>() {

    override fun decideRepository(repositoryClass: KClass<CustomerRepository>) = beanConfig.getBean(repositoryClass)

    override fun createTypeEntity(common: Member) = Customer(common)
}

