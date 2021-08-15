package com.kotlin.delivery.common.property

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.security.Key

@ConstructorBinding
@ConfigurationProperties(prefix = "auth.jwt")
class JwtProperty(

    secret: String,

    val accessDuration: Long,

    val refreshDuration: Long
) {

    val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
}
