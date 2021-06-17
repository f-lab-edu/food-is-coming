package com.kotlin.delivery.auth.dao

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class AuthRepository(

    @Qualifier("authRedisTemplate")
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun insertAuth(mobile: String, auth: String) =
        redisTemplate.opsForValue().set(mobile, auth, Duration.ofMinutes(3L))

    fun searchAuth(mobile: String, auth: String) = redisTemplate.opsForValue().get(mobile)

    fun deleteAuth(mobile: String) = redisTemplate.delete(mobile)
}
