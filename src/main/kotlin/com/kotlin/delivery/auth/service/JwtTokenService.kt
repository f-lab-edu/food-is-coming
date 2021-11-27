package com.kotlin.delivery.auth.service

import com.kotlin.delivery.auth.dao.AuthRepository
import com.kotlin.delivery.auth.dto.TokenSet
import com.kotlin.delivery.common.exception.AuthNotFoundException
import com.kotlin.delivery.common.properties.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.security.authentication.BadCredentialsException
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

    private val prop: JwtProperties,

    private val authRepository: AuthRepository
) {
    fun createTokenSet(authentication: Authentication): TokenSet {
        val authorities = authentication.authorities.joinToString()
        val accessToken = generateAccessToken(authentication, authorities)
        val refreshToken = generateRefreshToken()

        return TokenSet(accessToken = accessToken, refreshToken = refreshToken)
    }

    fun createAuthenticationToken(tokenParsed: Jws<Claims>): Authentication {
        val tokenBody = tokenParsed.body

        val authorities = tokenBody["auth"].toString().split(",")
            .map { SimpleGrantedAuthority(it) }
        val principal = User(tokenBody.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun reissueToken(tokenSet: TokenSet): TokenSet {
        parseToken(tokenSet.refreshToken)

        val authentication = createAuthenticationToken(parseToken(tokenSet.accessToken))
        val refreshTokenSaved = authRepository.select(authentication.name)
            ?: throw AuthNotFoundException("이미 로그아웃 한 사용자입니다.")

        if (tokenSet.refreshToken != refreshTokenSaved) {
            throw BadCredentialsException("토큰에 담긴 사용자 정보가 일치하지 않습니다.")
        }

        return createTokenSet(authentication).also {
            authRepository.insert(authentication.name, it.refreshToken)
        }
    }

    fun saveRefreshToken(email: String, refreshToken: String) {
        authRepository.insert(email, refreshToken, prop.refreshDuration)
    }

    /**
     * possible exception description
     *
     * 1. SignatureException: signature in a Jwt token not verified
     * 2. MalformedJwtException: no valid composition for a given Jwt token
     * 3. ExpiredJwtException: an expired Jwt token comes in
     * 4. UnsupportedJwtException: a Jwt token that has unexpected formats
     * 5. IllegalArgumentException: when `null` or `empty string` or `white space` has been provided
     */
    fun parseToken(token: String): Jws<Claims> =
        try {
            Jwts.parserBuilder().setSigningKey(prop.key).build().parseClaimsJws(token)
        } catch (e: RuntimeException) {
            when (e) {
                is SignatureException -> throw SignatureException("서명이 잘못된 토큰입니다.", e)
                is MalformedJwtException -> throw MalformedJwtException("올바르게 생성된 토큰이 아닙니다.", e)
                is ExpiredJwtException -> throw ExpiredJwtException(e.header, e.claims, "이미 만료된 토큰입니다.", e)
                is UnsupportedJwtException -> throw UnsupportedJwtException("지원되지 않는 형식의 토큰입니다.", e)
                else -> throw IllegalArgumentException("토큰이 입력되지 않았습니다.", e)
            }
        }

    private fun generateAccessToken(authentication: Authentication, authorities: String) =
        Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .setExpiration(expireIn(prop.accessDuration))
            .signWith(prop.key, SignatureAlgorithm.HS512)
            .compact()

    private fun generateRefreshToken() =
        Jwts.builder()
            .setExpiration(expireIn(prop.refreshDuration))
            .signWith(prop.key, SignatureAlgorithm.HS256)
            .compact()

    private fun expireIn(duration: Long) = Date.from(
        LocalDateTime.now().plusMinutes(duration).atZone(ZoneId.systemDefault()).toInstant()
    )
}
