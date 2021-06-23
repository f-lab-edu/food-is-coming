package com.kotlin.delivery.auth.service

interface AuthService {

    fun sendAuth(mobile: String)

    fun verifyAuth(mobile: String, auth: String)
}
