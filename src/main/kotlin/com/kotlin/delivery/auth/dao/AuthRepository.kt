package com.kotlin.delivery.auth.dao

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

const val VALID_AUTH_TIME = 3L

@Repository
class AuthRepository(

    @Qualifier("authRedisTemplate")
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun insert(key: String, value: String) = insert(key, value, VALID_AUTH_TIME)

    fun insert(key: String, value: String, expiration: Long) =
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(expiration))

    fun select(key: String): String? = redisTemplate.opsForValue().get(key)

    fun delete(key: String) = redisTemplate.delete(key)
}
