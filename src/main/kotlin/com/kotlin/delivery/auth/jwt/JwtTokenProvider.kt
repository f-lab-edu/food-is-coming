package com.kotlin.delivery.auth.jwt

import com.kotlin.delivery.common.property.JwtProperty
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Component
class JwtTokenProvider(private val prop: JwtProperty) {

    val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(prop.secretKey))

    fun generateToken(authentication: Authentication): String {
        val authorities = authentication.authorities.joinToString()

        val expiration = Date.from(
            LocalDateTime.now().plusMinutes(prop.duration).atZone(ZoneId.systemDefault()).toInstant()
        )

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .setExpiration(expiration)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }
}
