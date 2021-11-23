package com.kotlin.delivery.auth.dto

data class TokenSet(

    val type: String = "Bearer",

    val accessToken: String,

    val refreshToken: String
)
