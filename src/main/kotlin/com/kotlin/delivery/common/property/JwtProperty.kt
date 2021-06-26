package com.kotlin.delivery.common.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "auth.jwt")
class JwtProperty(val secretKey: String, val duration: Long)
