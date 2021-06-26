package com.kotlin.delivery.auth.service

import com.kotlin.delivery.common.property.JwtProperty
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.security.SignatureException
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Service
class JwtTokenService(

    private val prop: JwtProperty,
) {
    fun createToken(authentication: Authentication): String {
        val authorities = authentication.authorities.joinToString()

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .setExpiration(getExpiration())
            .signWith(prop.key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun createAuthenticationToken(parsedToken: Claims): Authentication {
        val authorities = parsedToken["auth"].toString().split(",").map { SimpleGrantedAuthority(it) }
        val principal = User(parsedToken.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun parseToken(token: String): Claims =
        try {
            Jwts.parserBuilder().setSigningKey(prop.key).build().parseClaimsJws(token).body
        } catch (e: RuntimeException) {
            when (e) {
                is SignatureException -> { throw SignatureException("서명이 잘못된 토큰입니다.", e) }
                is MalformedJwtException -> { throw MalformedJwtException("올바르게 생성된 토큰이 아닙니다.", e) }
                is ExpiredJwtException -> { throw ExpiredJwtException(e.header, e.claims, "이미 만료된 토큰입니다.", e) }
                is UnsupportedJwtException -> { throw UnsupportedJwtException("지원되지 않는 형식의 토큰입니다.", e) }
                else -> { throw IllegalArgumentException("토큰이 입력되지 않았습니다.", e) }
            }
        }

    private fun getExpiration() = Date.from(
        LocalDateTime.now().plusMinutes(prop.duration).atZone(ZoneId.systemDefault()).toInstant()
    )
}
