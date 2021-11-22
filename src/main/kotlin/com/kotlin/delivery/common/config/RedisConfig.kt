package com.kotlin.delivery.common.config

import com.kotlin.delivery.common.properties.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
class RedisConfig(private val prop: RedisProperties) {

    @Bean
    fun redisConnectionFactory() = LettuceConnectionFactory(prop.host, prop.port)

    @Bean("authRedisTemplate")
    fun authRedisTemplate() = StringRedisTemplate(redisConnectionFactory())
}
