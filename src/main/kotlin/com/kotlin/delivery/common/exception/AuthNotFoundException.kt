package com.kotlin.delivery.common.exception

import org.springframework.security.core.AuthenticationException

class AuthNotFoundException(msg: String) : AuthenticationException(msg)
