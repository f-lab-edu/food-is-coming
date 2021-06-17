package com.kotlin.delivery.common.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "sms.auth-api")
data class SMSAuthProperty(

    val apiKey: String,

    val secret: String,

    val sender: String,

    val type: String
)
