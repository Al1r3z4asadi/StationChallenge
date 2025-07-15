package com.chargepoint.auth.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuthServiceImplTest {

    private val authService: AuthService = AuthServiceImpl()

    @Test
    fun `given an allowed token, should return ALLOWED`() {

        val allowedToken = "validDriverToken123"

        val result = authService.checkACL(allowedToken)

        assertEquals(AuthorizationStatus.ALLOWED, result)
    }

    @Test
    fun `given a not allowed token, should return NOT_ALLOWED`() {

        val notAllowedToken = "explicitlyBannedToken"

        val result = authService.checkACL(notAllowedToken)

        assertEquals(AuthorizationStatus.NOT_ALLOWED, result)
    }

    @Test
    fun `given an unknown token, should return UNKNOWN`() {
        val unknownToken = "some-other-token-that-does-not-exist"

        val result = authService.checkACL(unknownToken)

        assertEquals(AuthorizationStatus.UNKNOWN, result)
    }
}