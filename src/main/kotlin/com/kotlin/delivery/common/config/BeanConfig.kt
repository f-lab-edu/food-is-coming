package com.kotlin.delivery.common.config

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import kotlin.reflect.KClass

@Configuration
class BeanConfig : ApplicationContextAware {

    lateinit var context: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.context = applicationContext
    }

    fun <T: Any> getBean(beanClass: KClass<T>): T = context.getBean(beanClass.java)
}
