package com.kotlin.delivery.auth.service

interface AuthService {

    fun sendAuth(to: String)

    fun verifyAuth(to: String, inputAuth: String)
}
