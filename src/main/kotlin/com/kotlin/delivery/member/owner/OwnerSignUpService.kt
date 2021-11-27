package com.kotlin.delivery.member.owner

import com.kotlin.delivery.common.config.BeanConfig
import com.kotlin.delivery.common.entity.Member
import com.kotlin.delivery.member.common.service.AbstractSignUpService
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class OwnerSignUpService(val beanConfig: BeanConfig) : AbstractSignUpService<Owner, OwnerRepository>() {

    override fun decideRepository(repositoryClass: KClass<OwnerRepository>) = beanConfig.getBean(repositoryClass)

    override fun createTypeEntity(common: Member) = Owner(common)
}
