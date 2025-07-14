package com.chargepoint.auth.service

import org.springframework.stereotype.Service

@Service
class AuthServiceImpl() : AuthService
{
    private val allowedTokens = setOf(
        "validDriverToken123",
        "anotherValidToken-abc-def"
    )

    private val notAllowedTokens = setOf(
        "explicitlyBannedToken"
    )
    override fun checkACL(driverToken: String): AuthorizationStatus {
        return when {
            allowedTokens.contains(driverToken) -> AuthorizationStatus.ALLOWED
            notAllowedTokens.contains(driverToken) -> AuthorizationStatus.NOT_ALLOWED
            else -> AuthorizationStatus.UNKNOWN
        }
    }
}