package com.kotlin.delivery.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Profile

@Profile("prod")
@ConstructorBinding
@ConfigurationProperties(prefix = "sms.auth-api")
open class SMSAuthProperties(

    val apiKey: String,

    val secret: String,

    val sender: String,

    val type: String
)
